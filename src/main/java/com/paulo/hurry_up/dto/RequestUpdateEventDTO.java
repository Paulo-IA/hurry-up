package com.paulo.hurry_up.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateEventDTO {

    @NotBlank(message = "O campo 'Nome' deve ser preenchido!")
    @Size(min = 3, max = 100, message = "O campo 'Nome' deve ter entre 2 e 40 caracteres!")
    private String name;

    @Size(max = 140, message = "O campo 'Descrição' deve ter no máximo 140 caracteres!")
    String description;

    @NotNull(message = "O campo 'Data' deve ser preenchido!")
    @Future(message = "A data deve ser futura!")
    private ZonedDateTime date;
}
