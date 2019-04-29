package com.kabat.petfinder.utils;

import com.kabat.petfinder.dtos.CoordinatesDto;
import com.kabat.petfinder.dtos.FoundPetDto;
import com.kabat.petfinder.entities.Coordinates;
import com.kabat.petfinder.entities.FoundPet;

public class FoundPetMapper {

    public static FoundPetDto mapToDto(FoundPet foundPet) {
        return FoundPetDto.aFoundPetDto()
                .additionalInfo(foundPet.getAdditionalInfo())
                .email(foundPet.getEmail())
                .phoneNumber(foundPet.getPhoneNumber())
                .coordinates(
                        CoordinatesDto.aCoordinatesDto()
                                .latitude(foundPet.getCoordinates().getLatitude())
                                .longitude(foundPet.getCoordinates().getLongitude())
                                .build()
                )
                .gender(foundPet.getGender())
                .whenSeen(foundPet.getWhenSeen())
                .createdAt(foundPet.getCreatedAt())
                .id(foundPet.getId())
                .type(foundPet.getType())
                .pictureUrls(foundPet.getPictureUrls())
                .build();
    }

    public static FoundPet mapToEntity(FoundPetDto foundPetDto) {
        return FoundPet.aFoundPet()
                .additionalInfo(foundPetDto.getAdditionalInfo())
                .email(foundPetDto.getEmail())
                .phoneNumber(foundPetDto.getPhoneNumber())
                .coordinates(
                        Coordinates.aCoordinates()
                                .latitude(foundPetDto.getCoordinates().getLatitude())
                                .longitude(foundPetDto.getCoordinates().getLongitude())
                                .build()
                )
                .gender(foundPetDto.getGender())
                .whenSeen(foundPetDto.getWhenSeen())
                .createdAt(foundPetDto.getCreatedAt())
                .id(foundPetDto.getId())
                .type(foundPetDto.getType())
                .pictureUrls(foundPetDto.getPictureUrls())
                .build();
    }
}
