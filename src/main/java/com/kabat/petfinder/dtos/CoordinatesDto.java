package com.kabat.petfinder.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(builderMethodName = "aCoordinatesDto")
public class CoordinatesDto {
    private BigDecimal longitude;
    private BigDecimal latitude;
}
