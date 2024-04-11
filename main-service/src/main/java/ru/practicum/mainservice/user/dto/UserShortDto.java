package ru.practicum.mainservice.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserShortDto {

    private Long id;

    @NotBlank(message = "name cannot consist only of spaces")
    private String name;

}
