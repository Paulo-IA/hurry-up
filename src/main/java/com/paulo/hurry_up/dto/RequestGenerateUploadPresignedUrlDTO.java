package com.paulo.hurry_up.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestGenerateUploadPresignedUrlDTO {
    @NotBlank(message = "O campo 'originalFileName' deve ser preenchido!")
    private String originalFileName;

    private Map<String, String> metadata;
}
