package ru.practicum.mainservice.category.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CategoryDto {

    private Long id;

    @NotBlank(message = "category name cannot be empty and consist only of spaces")
    private String name;

}
