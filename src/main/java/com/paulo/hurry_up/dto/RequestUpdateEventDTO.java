package com.paulo.hurry_up.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String name;

    private String description;

    @NotNull(message = "O campo 'Data' deve ser preenchido!")
    @Future(message = "A data deve ser futura!")
    private ZonedDateTime date;
}
