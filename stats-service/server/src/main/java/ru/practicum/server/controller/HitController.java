package ru.practicum.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.server.service.HitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String HIT_PREFIX = "/hit";
    private static final String STATS_PREFIX = "/stats";

    private final HitService hitService;

    @PostMapping(HIT_PREFIX)
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody HitDto hitDto) {

        log.info("catch hit creation request");
        hitService.addHit(hitDto);
    }

    @GetMapping(STATS_PREFIX)
    public List<StatsDto> findStats(@RequestParam("start") String start,
                                    @RequestParam("end") String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, DATE_FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, DATE_FORMATTER);

        log.info("catch find stats request");
        return hitService.findStats(startTime, endTime, uris, unique);
    }

}
