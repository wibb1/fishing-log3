// TideDTO.java
package com.fishingLog.spring.dto;

import com.fishingLog.spring.model.Tide;
import com.fishingLog.spring.model.TideStation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TideDTO {
    private Long id;
    private Double height;
    private Instant time;
    private String type;
    private TideStation tideStation;

    public TideDTO(Tide tide) {
        this.id = tide.getId();
        this.height = tide.getHeight();
        this.time = tide.getTime();
        this.type = tide.getType();
        this.tideStation = tide.getTideStation();
    }
}
