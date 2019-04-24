package com.kabat.petfinder.dtos;

import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.PetType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "aLostPetDto")
public class LostPetDto {
    private Long id;
    private String name;
    private String additionalInfo;
    private String phoneNumber;
    private String email;
    private PetType type;
    private Gender gender;
    private String pictureUrl;
    private CoordinatesDto coordinates;
    private LocalDateTime lastSeen;
    private LocalDateTime createdAt;
}
