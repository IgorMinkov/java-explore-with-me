package ru.practicum.server.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static HitDto toHitDto(Hit hit) {

        return HitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().toString())
                .build();
    }

    public static Hit toHit(HitDto hitDto) {

        return Hit.builder()
                .id(hitDto.getId())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), DATE_FORMATTER))
                .build();
    }

}
