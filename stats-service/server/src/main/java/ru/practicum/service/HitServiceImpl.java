package ru.practicum.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatsDto;
import ru.practicum.model.Hit;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    @Transactional
    public void addHit(Hit hit) {
        hitRepository.save(hit);
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
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
