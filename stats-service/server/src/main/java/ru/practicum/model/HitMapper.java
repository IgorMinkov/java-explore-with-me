package ru.practicum.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;

import static ru.practicum.Utils.STATS_FORMATTER;

@UtilityClass
public class HitMapper {

    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), STATS_FORMATTER))
                .build();
    }

}
