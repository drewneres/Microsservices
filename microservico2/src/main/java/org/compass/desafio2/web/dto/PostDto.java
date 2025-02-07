package org.compass.desafio2.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
