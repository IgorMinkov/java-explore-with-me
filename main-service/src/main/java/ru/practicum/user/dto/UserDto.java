package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {

    private Long id;

    @Size(min = 2, max = 250, message = "name must be greater than 2 and less than 250")
    @NotBlank(message = "name cannot consist only of spaces")
    private String name;


    @Email
    @Size(min = 6, max = 254, message = "Email must be greater than 6 and less than 254")
    @NotBlank(message = "email cannot be empty and consist only of spaces.")
    private String email;

}
