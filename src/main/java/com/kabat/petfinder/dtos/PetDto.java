package com.kabat.petfinder.dtos;

import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.PetStatus;
import com.kabat.petfinder.entities.PetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aPetDto")
public class PetDto {
    private UUID id;
    private String name;
    private String additionalInfo;
    private String phoneNumber;
    @NonNull
    private String email;
    @NonNull
    private PetType type;
    private Gender gender;
    @NonNull
    private PetStatus status;
    @Builder.Default
    private List<PictureDto> pictures = new ArrayList<>();
    @NonNull
    private CoordinatesDto coordinates;
    private LocalDateTime lastSeen;
    private LocalDateTime createdAt;
}
