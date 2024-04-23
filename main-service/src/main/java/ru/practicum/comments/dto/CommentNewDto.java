package ru.practicum.comments.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class CommentNewDto {

    @NotBlank(message = "message cannot be empty and consist only of spaces.")
    @Size(min = 20, max = 500, message = "message must less than 500")
    private String message;

}
