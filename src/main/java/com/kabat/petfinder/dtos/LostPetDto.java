package com.kabat.petfinder.dtos;

import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.PetType;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "aLostPetDto")
public class LostPetDto {
    private Long id;
    @NonNull
    private String name;
    private String additionalInfo;
    private String phoneNumber;
    private String email;
    @NonNull
    private PetType type;
    @NonNull
    private Gender gender;
    private String pictureUrl;
    @NonNull
    private CoordinatesDto coordinates;
    private LocalDateTime lastSeen;
    private LocalDateTime createdAt;
}
