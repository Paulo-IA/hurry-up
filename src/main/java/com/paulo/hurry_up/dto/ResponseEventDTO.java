package com.paulo.hurry_up.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEventDTO {
    private UUID id;
    private String name;
    private String description;
    private ZonedDateTime date;
    private ZonedDateTime createdAt;
    private DaysToGo daysToGo;
}
