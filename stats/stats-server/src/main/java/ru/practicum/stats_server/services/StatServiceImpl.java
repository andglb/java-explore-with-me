package ru.practicum.stats_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.stats_server.mappers.EndpointHitMapper;
import ru.practicum.stats_server.mappers.ViewStatsMapper;
import ru.practicum.stats_server.repositories.StatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statServerRepository;
    private final EndpointHitMapper endpointHitMapper;
    private final ViewStatsMapper viewStatsMapper;

    @Override
    public void saveHit(EndpointHitDto endpointHitDto) {
        log.debug("Save hit by app: " + endpointHitDto.getApp());
        statServerRepository.save(endpointHitMapper.toEntity(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            if (uris == null || uris.size() == 0) {
                return statServerRepository.findDistinctViewsAll(start, end).stream().map(viewStatsMapper::toViewStatsDto).collect(Collectors.toList());
            } else {
                return statServerRepository.findDistinctViews(start, end, uris).stream().map(viewStatsMapper::toViewStatsDto).collect(Collectors.toList());
            }
        } else {
            if (uris == null || uris.size() == 0) {
                return statServerRepository.findViewsAll(start, end).stream().map(viewStatsMapper::toViewStatsDto).collect(Collectors.toList());
            } else {
                return statServerRepository.findViews(start, end, uris).stream().map(viewStatsMapper::toViewStatsDto).collect(Collectors.toList());
            }
        }
    }
}