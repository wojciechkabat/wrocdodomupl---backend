package com.kabat.petfinder.utils;

import com.kabat.petfinder.dtos.CoordinatesDto;
import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.entities.Coordinates;
import com.kabat.petfinder.entities.LostPet;

public class LostPetMapper {

    public static LostPetDto mapToDto(LostPet lostPet) {
        return LostPetDto.aLostPetDto()
                .name(lostPet.getName())
                .additionalInfo(lostPet.getAdditionalInfo())
                .email(lostPet.getEmail())
                .phoneNumber(lostPet.getPhoneNumber())
                .coordinates(
                        CoordinatesDto.aCoordinatesDto()
                                .latitude(lostPet.getCoordinates().getLatitude())
                                .longitude(lostPet.getCoordinates().getLongitude())
                                .build()
                )
                .gender(lostPet.getGender())
                .lastSeen(lostPet.getLastSeen())
                .createdAt(lostPet.getCreatedAt())
                .id(lostPet.getId())
                .type(lostPet.getType())
                .pictureUrls(lostPet.getPictureUrls())
                .build();
    }

    public static LostPet mapToEntity(LostPetDto lostPetDto) {
        return LostPet.aLostPet()
                .name(lostPetDto.getName())
                .additionalInfo(lostPetDto.getAdditionalInfo())
                .email(lostPetDto.getEmail())
                .phoneNumber(lostPetDto.getPhoneNumber())
                .coordinates(
                        Coordinates.aCoordinates()
                                .latitude(lostPetDto.getCoordinates().getLatitude())
                                .longitude(lostPetDto.getCoordinates().getLongitude())
                                .build()
                )
                .gender(lostPetDto.getGender())
                .lastSeen(lostPetDto.getLastSeen())
                .createdAt(lostPetDto.getCreatedAt())
                .id(lostPetDto.getId())
                .type(lostPetDto.getType())
                .pictureUrls(lostPetDto.getPictureUrls())
                .build();
    }
}
