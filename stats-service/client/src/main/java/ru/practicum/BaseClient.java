//package ru.practicum;
//
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
//import org.springframework.web.client.HttpStatusCodeException;
//import org.springframework.web.client.RestTemplate;
//import ru.practicum.dto.StatsDto;
//
//import java.util.List;
//import java.util.Map;
//
//public class BaseClient {
//    protected final RestTemplate rest;
//
//    public BaseClient(RestTemplate rest) {
//        this.rest = rest;
//    }
//
//    protected <T> ResponseEntity<Object> post(String path, T body) {
//        return makeAndSendRequest(HttpMethod.POST, path, null, body);
//    }
//
//    protected ResponseEntity<List<StatsDto>> get(String path, @Nullable Map<String, Object> parameters) {
//        return makeAndSendStat(HttpMethod.GET, path, parameters);
//    }
//
//    private <T> ResponseEntity<Object> makeAndSendRequest(
//            HttpMethod method,
//            String path,
//            @Nullable Map<String, Object> parameters,
//            @Nullable T body) {
//        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
//
//        ResponseEntity<Object> statsServiceResponse;
//        try {
//            if (parameters != null) {
//                statsServiceResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
//            } else {
//                statsServiceResponse = rest.exchange(path, method, requestEntity, Object.class);
//            }
//        } catch (HttpStatusCodeException e) {
//            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
//        }
//        return prepareGatewayResponse(statsServiceResponse);
//    }
//
//    private ResponseEntity<List<StatsDto>> makeAndSendStat(
//            HttpMethod method,
//            String path,
//            @Nullable Map<String, Object> parameters) {
//        HttpEntity<List<StatsDto>> requestEntity = new HttpEntity<>(null, defaultHeaders());
//        ResponseEntity<List<StatsDto>> statsServiceResponse;
//        try {
//            if (parameters != null) {
//                statsServiceResponse = rest.exchange(path, method, requestEntity,
//                        new ParameterizedTypeReference<>() {}, parameters);
//            } else {
//                statsServiceResponse = rest.exchange(path, method, requestEntity,
//                        new ParameterizedTypeReference<>() {});
//            }
//        } catch (HttpStatusCodeException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//        return prepareStatResponse(statsServiceResponse);
//    }
//
//    private HttpHeaders defaultHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//        return headers;
//    }
//
//    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response;
//        }
//
//        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
//
//        if (response.hasBody()) {
//            return responseBuilder.body(response.getBody());
//        }
//
//        return responseBuilder.build();
//    }
//
//    private static ResponseEntity<List<StatsDto>> prepareStatResponse(ResponseEntity<List<StatsDto>> response) {
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response;
//        }
//        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
//        if (response.hasBody()) {
//            return responseBuilder.body(response.getBody());
//        }
//        return responseBuilder.build();
//    }
//
//}
