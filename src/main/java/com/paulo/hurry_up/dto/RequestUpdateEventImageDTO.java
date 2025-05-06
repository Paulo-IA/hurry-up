package com.paulo.hurry_up.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateEventImageDTO {
    @NotBlank(message = "O campo 'imageUrl' não pode ser vazio")
    private String imageUrl;
}
