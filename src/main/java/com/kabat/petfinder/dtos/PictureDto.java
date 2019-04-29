package com.kabat.petfinder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aPictureDto")
public class PictureDto {
    private String pictureId;
    private String pictureUrl;
}
