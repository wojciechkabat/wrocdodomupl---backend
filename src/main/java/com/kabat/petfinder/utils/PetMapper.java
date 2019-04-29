package com.kabat.petfinder.utils;

import com.kabat.petfinder.dtos.CoordinatesDto;
import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.dtos.PictureDto;
import com.kabat.petfinder.entities.Coordinates;
import com.kabat.petfinder.entities.Pet;
import com.kabat.petfinder.entities.PetPicture;

import static java.util.stream.Collectors.toList;

public class PetMapper {

    public static PetDto mapToDto(Pet pet) {
        return PetDto.aPetDto()
                .id(pet.getId())
                .name(pet.getName())
                .status(pet.getStatus())
                .additionalInfo(pet.getAdditionalInfo())
                .email(pet.getEmail())
                .phoneNumber(pet.getPhoneNumber())
                .coordinates(
                        CoordinatesDto.aCoordinatesDto()
                                .latitude(pet.getCoordinates().getLatitude())
                                .longitude(pet.getCoordinates().getLongitude())
                                .build()
                )
                .gender(pet.getGender())
                .lastSeen(pet.getLastSeen())
                .createdAt(pet.getCreatedAt())
                .type(pet.getType())
                .pictures(
                        pet.getPictures()
                                .stream()
                                .map(picture -> PictureDto.aPictureDto()
                                        .pictureId(picture.getPictureId())
                                        .pictureUrl(picture.getPictureUrl())
                                        .build())
                                .collect(toList())
                )
                .build();
    }

    public static Pet mapToEntity(PetDto petDto) {
        return Pet.aPet()
                .id(petDto.getId())
                .name(petDto.getName())
                .additionalInfo(petDto.getAdditionalInfo())
                .email(petDto.getEmail())
                .phoneNumber(petDto.getPhoneNumber())
                .coordinates(
                        Coordinates.aCoordinates()
                                .latitude(petDto.getCoordinates().getLatitude())
                                .longitude(petDto.getCoordinates().getLongitude())
                                .build()
                )
                .gender(petDto.getGender())
                .status(petDto.getStatus())
                .lastSeen(petDto.getLastSeen())
                .createdAt(petDto.getCreatedAt())
                .type(petDto.getType())
                .pictures(
                        petDto.getPictures()
                                .stream()
                                .map(pictureDto -> PetPicture.aPetPicture()
                                        .petId(petDto.getId())
                                        .pictureId(pictureDto.getPictureId())
                                        .pictureUrl(pictureDto.getPictureUrl())
                                        .build())
                                .collect(toList())
                )
                .build();
    }
}
