package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Astrological;
import com.fishingLog.spring.utils.MoonPhase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class AstrologicalDTO {
    private Long id;
    private Instant astronomicalDawn;
    private Instant astronomicalDusk;
    private Instant civilDawn;
    private Instant civilDusk;
    private Instant moonrise;
    private Instant moonset;
    private Instant sunrise;
    private Instant sunset;
    private Instant time;
    private Instant nauticalDawn;
    private Instant nauticalDusk;
    private Double moonFraction;
    private MoonPhase closestMoonPhase;
    private MoonPhase currentMoonPhase;

    public AstrologicalDTO(Astrological astrological) {
        this.id = astrological.getId();
        this.astronomicalDawn = astrological.getAstronomicalDawn();
        this.astronomicalDusk = astrological.getAstronomicalDusk();
        this.civilDawn = astrological.getCivilDawn();
        this.civilDusk = astrological.getCivilDusk();
        this.moonrise = astrological.getMoonrise();
        this.moonset = astrological.getMoonset();
        this.sunrise = astrological.getSunrise();
        this.sunset = astrological.getSunset();
        this.time = astrological.getTime();
        this.nauticalDawn = astrological.getNauticalDawn();
        this.nauticalDusk = astrological.getNauticalDusk();
        this.moonFraction = astrological.getMoonFraction();
        this.closestMoonPhase = astrological.getClosestMoonPhase();
        this.currentMoonPhase = astrological.getCurrentMoonPhase();
    }
}
