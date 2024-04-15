package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.HitMapper;
import ru.practicum.service.HitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.Utils.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {

    private final HitService hitService;

    @PostMapping(HIT_PREFIX)
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody HitDto hitDto) {

        log.info("catch hit creation request");
        hitService.addHit(HitMapper.toHit(hitDto));
    }

    @GetMapping(STATS_PREFIX)
    public List<StatsDto> findStats(@RequestParam("start") String start,
                                    @RequestParam("end") String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, STATS_FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, STATS_FORMATTER);

        log.info("catch find stats request");
        return hitService.getStats(startTime, endTime, uris, unique);
    }

}
