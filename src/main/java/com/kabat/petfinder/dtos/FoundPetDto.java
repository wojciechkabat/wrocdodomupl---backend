package com.kabat.petfinder.dtos;

import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.PetType;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(builderMethodName = "aFoundPetDto")
public class FoundPetDto {
    private Long id;
    private String additionalInfo;
    private String phoneNumber;
    private String email;
    @NonNull
    private PetType type;
    @NonNull
    private Gender gender;
    private List<String> pictureUrls;
    @NonNull
    private CoordinatesDto coordinates;
    private LocalDateTime whenSeen;
    private LocalDateTime createdAt;
}
