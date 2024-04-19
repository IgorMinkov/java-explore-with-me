//package ru.practicum;
//
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.DefaultUriBuilderFactory;
//
//@Configuration
//public class StatsClientNewConfig {
//
//    private final String serverUrl = "http://localhost:9090";
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
//                .build();
//    }
//
//}
