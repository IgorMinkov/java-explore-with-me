package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsParamsDto {

    LocalDateTime start;

    LocalDateTime end;

    List<String> uris;

    boolean unique;

}
