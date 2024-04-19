//package ru.practicum;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import ru.practicum.dto.HitDto;
//import ru.practicum.dto.StatsDto;
//import ru.practicum.dto.StatsParamsDto;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class StatisticClientNew {
//
//    private final RestTemplate restTemplate;
//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    public StatsDto addHit(HitDto newStatsDto) {
//        HttpEntity<HitDto> requestEntity = new HttpEntity<>(newStatsDto, defaultHeaders());
//        return restTemplate.postForObject("/hit", requestEntity, StatsDto.class);
//    }
//
//    public List<StatsDto> findStats(StatsParamsDto statsParamsDto) {
//        String url;
//        Map<String, Object> params;
//        if (statsParamsDto.getUris() == null) {
//            url = "/stats?start={start}&end={end}&unique={unique}";
//            params = Map.of(
//                    "start", getEncodedAndFormattedTime(statsParamsDto.getStart()),
//                    "end", getEncodedAndFormattedTime(statsParamsDto.getEnd()),
//                    "unique", statsParamsDto.isUnique());
//        } else {
//            url = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
//            params = Map.of(
//                    "start", getEncodedAndFormattedTime(statsParamsDto.getStart()),
//                    "end", getEncodedAndFormattedTime(statsParamsDto.getEnd()),
//                    "uris", getUrisAsString(statsParamsDto.getUris()),
//                    "unique", statsParamsDto.isUnique());
//        }
//        StatsDto[] result = restTemplate.getForObject(url, StatsDto[].class, params);
//        return Arrays.asList(result);
//    }
//
//    private HttpHeaders defaultHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//        return headers;
//    }
//
//    private String getUrisAsString(List<String> uris) {
//        return String.join(",", uris);
//    }
//
//    private String getEncodedAndFormattedTime(LocalDateTime time) {
//        return URLEncoder.encode(time.format(formatter), StandardCharsets.UTF_8);
//    }
//
//}
