package ru.practicum.server.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatsDto;
import ru.practicum.server.model.Hit;
import ru.practicum.server.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    public void addHit(Hit hit) {
        hitRepository.save(hit);
    }

    @Override
    public List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null) {
            if (unique) {
                log.info("Get all stats by uniq ip");
                return hitRepository.findAllStatsByUniqIp(start, end);
            } else {
                log.info("Get all stats");
                return hitRepository.findAllStats(start, end);
            }
        } else {
            if (unique) {
                log.info("Get all stats by uri and uniq ip");
                return hitRepository.findStatsByUrisByUniqIp(start, end, uris);
            } else {
                log.info("Get all stats by uri");
                return hitRepository.findStatsByUris(start, end, uris);
            }
        }
    }
}
