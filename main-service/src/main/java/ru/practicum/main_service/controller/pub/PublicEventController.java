package ru.practicum.main_service.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main_service.dto.event.EventFullDto;
import ru.practicum.main_service.enums.SortValue;
import ru.practicum.main_service.service.event.EventService;
import ru.practicum.main_service.service.statistics.StatisticsService;
import ru.practicum.stats_client.StatClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;
    private final StatisticsService statisticsService;

    @GetMapping
    public List<EventFullDto> getEventsWithParamsByUser(@RequestParam(name = "text", required = false) String text,
                                                        @RequestParam(name = "categories", required = false, defaultValue = "") List<Long> categories,
                                                        @RequestParam(name = "paid", required = false) Boolean paid,
                                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                                        @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") boolean onlyAvailable,
                                                        @RequestParam(name = "sort", required = false) SortValue sort,
                                                        @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                        HttpServletRequest request) {
        return eventService.getEventsWithParamsByUser(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Long id, HttpServletRequest request) {
        return eventService.getEvent(id, request);
    }
}