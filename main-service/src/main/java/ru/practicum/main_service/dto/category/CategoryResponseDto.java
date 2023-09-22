package ru.practicum.main_service.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class CategoryResponseDto {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}