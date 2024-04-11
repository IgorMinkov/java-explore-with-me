package ru.practicum.mainservice.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "name cannot consist only of spaces")
    private String name;


    @Email
    @NotEmpty(message = "email cannot consist only of spaces")
    private String email;


}
