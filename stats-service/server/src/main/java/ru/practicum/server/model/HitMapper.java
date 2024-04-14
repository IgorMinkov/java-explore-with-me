package ru.practicum.server.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;

@UtilityClass
public class HitMapper {

    public static HitDto toHitDto(Hit hit) {

        return HitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .build();
    }

    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .id(hitDto.getId())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }

}
