package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.entity.EndpointHit;
import ru.practicum.entity.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public EndpointHit createHit(EndpointHit endpointHit) {

        return statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(Timestamp start, Timestamp end, List<String> uris, boolean unique) {
        if (Boolean.TRUE.equals(unique)) {
            statsRepository.findStatsByDatesUniqueIp(start, end, uris);
        }
        return statsRepository.findStatsByDates(start, end, uris);
    }
}
