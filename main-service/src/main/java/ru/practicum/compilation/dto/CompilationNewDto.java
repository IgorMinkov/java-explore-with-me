package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationNewDto {

    private Set<Long> events;

    @Builder.Default
    private Boolean pinned = false;

    @NotBlank(message = "compilation title cannot be empty and consist only of spaces")
    @Size(min = 1, max = 50)
    private String title;

}
