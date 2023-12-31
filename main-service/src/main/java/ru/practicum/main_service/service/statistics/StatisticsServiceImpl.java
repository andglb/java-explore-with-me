package ru.practicum.main_service.service.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.main_service.entity.Event;
import ru.practicum.main_service.exceptions.WrongTimeException;
import ru.practicum.stats_client.StatClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.main_service.util.Constants.DATE;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatClient statClient;
    private final String datePattern = DATE;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

    @Override
    public void sendStat(Event event, HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String remoteAddr = request.getRemoteAddr();
        String nameService = "main-service";

        EndpointHitDto requestDto = new EndpointHitDto();
        requestDto.setTimestamp(now.format(dateFormatter));
        requestDto.setUri("/events");
        requestDto.setApp(nameService);
        requestDto.setIp(remoteAddr);
        statClient.addStats(requestDto);
        setView(event);
        sendStatForTheEvent(event.getId(), remoteAddr, now, nameService);
    }

    @Override
    public void sendStat(List<Event> events, HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String remoteAddr = request.getRemoteAddr();
        String nameService = "main-service";

        EndpointHitDto requestDto = new EndpointHitDto();
        requestDto.setTimestamp(now.format(dateFormatter));
        requestDto.setUri("/events");
        requestDto.setApp(nameService);
        requestDto.setIp(request.getRemoteAddr());
        statClient.addStats(requestDto);
        sendStatForEveryEvent(events, remoteAddr, LocalDateTime.now(), nameService);
    }

    @Override
    public void sendStatForTheEvent(Long eventId, String remoteAddr, LocalDateTime now,
                                    String nameService) {
        EndpointHitDto requestDto = new EndpointHitDto();
        requestDto.setTimestamp(now.format(dateFormatter));
        requestDto.setUri("/events/" + eventId);
        requestDto.setApp(nameService);
        requestDto.setIp(remoteAddr);
        statClient.addStats(requestDto);
    }

    @Override
    public void sendStatForEveryEvent(List<Event> events, String remoteAddr, LocalDateTime now,
                                      String nameService) {
        for (Event event : events) {
            EndpointHitDto requestDto = new EndpointHitDto();
            requestDto.setTimestamp(now.format(dateFormatter));
            requestDto.setUri("/events/" + event.getId());
            requestDto.setApp(nameService);
            requestDto.setIp(remoteAddr);
            statClient.addStats(requestDto);
        }
    }

    @Override
    public void setView(Event event) {
        String startTime = event.getCreatedOn().format(dateFormatter);
        String endTime = LocalDateTime.now().format(dateFormatter);
        List<String> uris = List.of("/events/" + event.getId());

        List<ViewStatsDto> stats = getStats(startTime, endTime, uris);
        if (stats.size() == 1) {
            event.setViews(stats.get(0).getHits());
        } else {
            event.setViews(0L);
        }
    }

    @Override
    public List<ViewStatsDto> getStats(String startTime, String endTime, List<String> uris) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);
        LocalDateTime startDate = LocalDateTime.parse(UriEncoder.decode(startTime), formatter);
        LocalDateTime endDate = LocalDateTime.parse(UriEncoder.decode(endTime), formatter);
        if (startDate.isAfter(endDate)) {
            throw new WrongTimeException("The start date of the search cannot be after the end date!");
        }
        return statClient.getStats(startTime, endTime, uris, false);
    }
}