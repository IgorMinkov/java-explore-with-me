package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class CompilationUpdateDto {

    private Set<Long> events;

    private Boolean pinned;

    @Size(min = 1, max = 50, message = "title must be greater than 1 and less than 50")
    private String title;

}
