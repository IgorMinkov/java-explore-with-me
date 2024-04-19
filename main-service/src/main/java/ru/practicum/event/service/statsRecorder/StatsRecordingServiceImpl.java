//package ru.practicum.event.service.statsRecorder;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.practicum.StatisticClientNew;
//import ru.practicum.dto.HitDto;
//import ru.practicum.dto.StatsDto;
//import ru.practicum.dto.StatsParamsDto;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class StatsRecordingServiceImpl implements StatsRecordingService {
//
//    private final StatisticClientNew statClient;
//
//    @Override
//    public StatsDto addHit(HitDto hitDto) {
//        return statClient.addHit(hitDto);
//    }
//
//    @Override
//    public List<StatsDto> getStats(StatsParamsDto statsParamsDto) {
//        return statClient.findStats(statsParamsDto);
//    }
//
//}
