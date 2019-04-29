package com.kabat.petfinder.utils;

import com.kabat.petfinder.dtos.CoordinatesDto;
import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.dtos.PictureDto;
import com.kabat.petfinder.entities.Coordinates;
import com.kabat.petfinder.entities.LostPet;
import com.kabat.petfinder.entities.LostPetPicture;

import static java.util.stream.Collectors.toList;

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
                .pictures(
                        lostPet.getPictures()
                                .stream()
                                .map(picture -> PictureDto.aPictureDto()
                                        .pictureId(picture.getPictureId())
                                        .pictureUrl(picture.getPictureUrl())
                                        .build())
                                .collect(toList())
                )
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
                .pictures(
                        lostPetDto.getPictures()
                                .stream()
                                .map(pictureDto -> LostPetPicture.aLostPetPicture()
                                        .petId(lostPetDto.getId())
                                        .pictureId(pictureDto.getPictureId())
                                        .pictureUrl(pictureDto.getPictureUrl())
                                        .build())
                        .collect(toList())
                )
                .build();
    }
}
