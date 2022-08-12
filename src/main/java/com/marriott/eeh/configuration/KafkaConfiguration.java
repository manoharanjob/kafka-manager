package com.marriott.eeh.configuration;

import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.confluent.kafka.schemaregistry.SchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.schemaregistry.json.JsonSchemaProvider;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;

@Configuration
public class KafkaConfiguration {

	private final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

	@Autowired
	private KafkaProperties kafkaProps;
	
	@Bean
	public AdminClient devAdminClient() {
		return AdminClient.create(kafkaProps.getDevBroker());
	}

	@Bean
	public SchemaRegistryClient schemaRegistryClient() {
		Map<String, String> schemaConfig = kafkaProps.getDevSchema();
		String schemaUrl = schemaConfig.remove("schema.registry.url");
		RestService restService = new RestService(schemaUrl);
		List<SchemaProvider> providers = Arrays.asList(new AvroSchemaProvider(), new JsonSchemaProvider(),
				new ProtobufSchemaProvider());
		return new CachedSchemaRegistryClient(restService, 10, providers, schemaConfig, null);
	}

	@Bean
	public HttpClient connectHttpClient() {
		SSLContext sslContext = null;
		try {
			Map<String, String> connectConfig = kafkaProps.getDevConnect();
			sslContext = SSLContext.getInstance("TLS");
			if (connectConfig.containsKey("connect.server.ssl.truststore.location")) {
				var keyManagers = getKeyManagersFromKeyStore(connectConfig.get("connect.server.ssl.keystore.location"),
						connectConfig.get("connect.server.ssl.keystore.password"),
						connectConfig.get("connect.server.ssl.key.password"));
				var trustManagers = getTrustManagersFromTrustStore(
						connectConfig.get("connect.server.ssl.truststore.location"),
						connectConfig.get("connect.server.ssl.truststore.password"));
				sslContext.init(keyManagers, trustManagers, null);
			} else {
				TrustManager[] trustAllCerts = new TrustManager[] {};
				sslContext.init(null, trustAllCerts, null);
			}
		} catch (Exception e) {
			log.error("Connect Http Client creation exception:{}", e.getMessage());
		}
		return HttpClient.newBuilder().sslContext(sslContext).build();
	}

	private TrustManager[] getTrustManagersFromTrustStore(String sslPath, String sslPassword)
			throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
		tmf.init(loadKeyStore(sslPath, sslPassword));
		return tmf.getTrustManagers();
	}

	private KeyManager[] getKeyManagersFromKeyStore(String sslPath, String sslPassword, String sslKeyPassword)
			throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException {
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
		kmf.init(loadKeyStore(sslPath, sslPassword), sslKeyPassword.toCharArray());
		return kmf.getKeyManagers();
	}

	private KeyStore loadKeyStore(String sslPath, String sslPassword)
			throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(Files.newInputStream(Path.of(sslPath)), sslPassword.toCharArray());
		return ks;
	}

}
