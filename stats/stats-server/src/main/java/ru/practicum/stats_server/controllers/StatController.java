package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.EndpointHitRequestDto;
import ru.practicum.dto.EndpointHitResponseDto;
import ru.practicum.dto.ViewStatsResponseDto;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;
    private final StatsMapper statsMapper;

    /**
     * Создаёт EndpointHit сохраняя информацию о запросе
     */
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitResponseDto create(@RequestBody @Valid EndpointHitRequestDto endpointHitRequestDto) {
        log.info("POST EndpointHit {}", endpointHitRequestDto);
        return statsMapper.toEndpointHitResponseDto(statsService.createHit(statsMapper.toEndpointHit(endpointHitRequestDto)));
    }

    /**
     * Возвращает статистику по посещениям в интервале дат, по списку uri
     */
    @GetMapping("/stats")
    public List<ViewStatsResponseDto> getStats(
            @RequestParam Timestamp start,
            @RequestParam Timestamp end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET stats start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return statsMapper.toListViewStatsResponseDto(statsService.getStats(start, end, uris, unique));
    }
}