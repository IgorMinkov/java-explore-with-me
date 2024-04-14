package ru.practicum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final DateTimeFormatter STATS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String STATS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String HIT_PREFIX = "/hit";
    public static final String STATS_PREFIX = "/stats";
    public static final LocalDateTime START_HISTORY = LocalDateTime.of(
            1970, 1, 1, 0, 0);

}
