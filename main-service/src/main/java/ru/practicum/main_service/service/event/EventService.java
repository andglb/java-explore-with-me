package ru.practicum.main_service.service.event;

import ru.practicum.main_service.dto.event.EventFullDto;
import ru.practicum.main_service.dto.event.EventShortDto;
import ru.practicum.main_service.dto.event.NewEventDto;
import ru.practicum.main_service.dto.event.UpdateEventAdminDto;
import ru.practicum.main_service.dto.event.UpdateEventUserDto;
import ru.practicum.main_service.entity.Event;
import ru.practicum.main_service.enums.EventState;
import ru.practicum.main_service.enums.SortValue;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getEvents(Long userId, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminDto updateEventAdminDto);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserDto updateEventUserDto);

    EventFullDto getEventByUser(Long userId, Long eventId);

    List<EventFullDto> getEventsWithParamsByAdmin(List<Long> users, EventState states, List<Long> categoriesId,
                                                  String rangeStart, String rangeEnd, Integer from, Integer size);

    List<EventFullDto> getEventsWithParamsByUser(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, Boolean onlyAvailable, SortValue sort, Integer from,
                                                 Integer size, HttpServletRequest request);

    EventFullDto getEvent(Long id, HttpServletRequest request);

    void setView(List<Event> events);
}