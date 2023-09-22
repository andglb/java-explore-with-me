package ru.practicum.stats_server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.stats_server.enity.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.stats_server.util.Constants.DATE;

public class ErrorHandler {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleWrongTimeOfEventException(final WrongTimeException exception) {
        return new ApiError(exception.getMessage(), "For the requested operation the conditions are not met.",
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(), LocalDateTime.now().format(dateFormatter));
    }
}
