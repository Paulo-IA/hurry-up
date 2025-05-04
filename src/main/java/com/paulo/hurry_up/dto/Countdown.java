package com.paulo.hurry_up.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Countdown {
    private static final Long DAYS_IN_THE_WEEK = 7L;

    private Long totalDays;
    private Integer workingDays;

    public Countdown(ZonedDateTime days) {
        this.totalDays = this.getDaysFromNow(days);
        this.workingDays = this.getWorkingDaysFromNow(days);
    }

    private Long getDaysFromNow(ZonedDateTime date) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        return Duration.between(now, date).toDays();
    }

    private Integer getWorkingDaysFromNow(ZonedDateTime date) {
        Long daysFromNow = this.getDaysFromNow(date);

        int weekendsDays = Math.round((float) daysFromNow / DAYS_IN_THE_WEEK) * 2;

        return daysFromNow.intValue() - weekendsDays;
    }
}
