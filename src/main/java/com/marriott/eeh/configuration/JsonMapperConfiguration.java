package com.marriott.eeh.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class JsonMapperConfiguration {

	@Autowired
    private Environment environment;

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        DefaultSerializerProvider defaultSerializerProvider = new DefaultSerializerProvider.Impl();
      //  module.addSerializer(java.time.LocalDateTime.class, new CustomLocalDateTimeSerializer(environment.getProperty("date-time-format")));
       // module.addSerializer(java.time.LocalDate.class, new CustomLocalDateSerializer(environment.getProperty("date-format")));
        //defaultSerializerProvider.setNullValueSerializer(new NullSerializer());
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
         //       .registerModule(module)
                //	.setSerializerProvider(defaultSerializerProvider)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }
    
}