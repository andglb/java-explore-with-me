package ru.practicum.main_service.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main_service.dto.category.CategoryDto;
import ru.practicum.main_service.dto.user.UserShortDto;
import ru.practicum.main_service.entity.Location;
import ru.practicum.main_service.enums.EventState;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.main_service.util.Constants.DATE;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventFullDto {
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Integer confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE)
    private String createdOn;
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE)
    private LocalDateTime eventDate;
    private Long id;
    private UserShortDto initiator;
    @NotNull
    private Location location;
    private Boolean paid;
    private Long participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    private Long views;
}