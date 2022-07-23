package com.marriott.eeh.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

import io.confluent.kafka.schemaregistry.SchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.schemaregistry.json.JsonSchemaProvider;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;

@Configuration
@PropertySource("classpath:${kafka.file.path}")
public class KafkaConfiguration {

	private final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

	@Value("${kafka.file.path}")
	private String kafkaFilePath;

	public static Properties kafkaProperties = new Properties();

	@PostConstruct
	public void initConfig() {
		Properties properties = new Properties();
		try (InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:" + kafkaFilePath))) {
			properties.load(is);
			kafkaProperties = properties;
		} catch (Exception e) {
			log.error("Kafka configuration properties file read excecption:{}", e.getMessage());
		}
	}

	@Bean
	public AdminClient adminClient() {
		return AdminClient.create(getBrokerConfig());
	}

	@Bean
	public SchemaRegistryClient schemaRegistryClient() {
		Map<String, String> schemaConfig = getSchemaConfig();
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
			Map<String, String> connectConfig = getConnectConfig();
			sslContext = SSLContext.getInstance("TLS");
			var keyManagers = getKeyManagersFromKeyStore(connectConfig.get("connect.server.ssl.keystore.location"),
					connectConfig.get("connect.server.ssl.keystore.password"),
					connectConfig.get("connect.server.ssl.key.password"));
			var trustManagers = getTrustManagersFromTrustStore(
					connectConfig.get("connect.server.ssl.truststore.location"),
					connectConfig.get("connect.server.ssl.truststore.password"));
			sslContext.init(keyManagers, trustManagers, null);
		} catch (Exception e) {
			log.error("Connect Http Client creation exception:{}", e.getMessage());
		}
		return HttpClient.newBuilder().sslContext(sslContext).build();
	}

	public Properties getBrokerConfig() {
		Properties brokerProperties = new Properties();
		kafkaProperties.entrySet()
				.stream()
				.filter(entry -> !entry.getKey().toString().contains("schema") && !entry.getKey().toString().contains("connect"))
				.forEach(entry -> brokerProperties.put(entry.getKey().toString(), entry.getValue().toString()));
		return brokerProperties;
	}
	
	public Map<String, String> getSchemaConfig() {
		return kafkaProperties.entrySet()
				.stream()
				.filter(entry -> entry.getKey().toString().contains("schema"))
				.collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));
	}
	
	public Map<String, String> getConnectConfig() {
		return kafkaProperties.entrySet()
				.stream()
				.filter(entry -> entry.getKey().toString().contains("connect"))
				.collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));
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