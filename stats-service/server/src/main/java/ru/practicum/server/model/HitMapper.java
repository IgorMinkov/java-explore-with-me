package ru.practicum.server.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;

@UtilityClass
public class HitMapper {

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
