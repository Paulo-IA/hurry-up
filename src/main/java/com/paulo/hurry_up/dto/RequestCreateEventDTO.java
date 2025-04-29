package com.paulo.hurry_up.dto;

import java.time.ZonedDateTime;

public record RequestCreateEventDTO(String name, String description, ZonedDateTime date) {
}
