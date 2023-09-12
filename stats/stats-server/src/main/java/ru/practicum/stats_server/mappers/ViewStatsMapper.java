package ru.practicum.stats_server.mappers;

import org.mapstruct.Mapper;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.stats_server.enity.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    List<ViewStatsDto> toEntityList(List<ViewStats> viewStats);

    ViewStatsDto toViewStatsDto(ViewStats viewStats);
}