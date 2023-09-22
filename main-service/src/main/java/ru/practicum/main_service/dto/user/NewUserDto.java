package ru.practicum.main_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NonNull
public class NewUserDto {
    @NotNull
    @Size(min = 2, max = 250)
    private String name;
    @NotNull
    @Size(min = 6, max = 254)
    @Email
    private String email;
}