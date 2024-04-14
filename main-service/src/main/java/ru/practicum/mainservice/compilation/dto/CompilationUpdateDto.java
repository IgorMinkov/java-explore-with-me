package ru.practicum.mainservice.compilation.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class CompilationUpdateDto {

    private Set<Long> events;

    private Boolean pinned;

    @Size(min = 3, max = 120, message = "title must be greater than 3 and less than 120")
    private String title;

}
