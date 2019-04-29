package com.kabat.petfinder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aCoordinatesDto")
public class CoordinatesDto {
    private BigDecimal longitude;
    private BigDecimal latitude;
}
