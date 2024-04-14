package ru.practicum.server.service;

import ru.practicum.dto.StatsDto;
import ru.practicum.server.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {

    void addHit(Hit hit);

    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
