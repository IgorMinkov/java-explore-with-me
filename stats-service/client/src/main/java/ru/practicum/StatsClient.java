package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.Utils.*;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addHit(HitDto hitDto) {
        return post(HIT_PREFIX, hitDto);
    }

    public ResponseEntity<List<StatsDto>> findStats(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start.format(STATS_FORMATTER),
                "end", end.format(STATS_FORMATTER),
                "uris", uris,
                "unique", unique
        );
        return get(STATS_PREFIX + "?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

}
