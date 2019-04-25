package com.kabat.petfinder.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aFoundPet")
@Table(name = "found_pets")
public class FoundPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "additionalInfo")
    private String additionalInfo;
    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull
    private PetType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "when_seen")
    @NotNull
    private LocalDateTime whenSeen;
    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;
    @Embedded
    @NotNull
    private Coordinates coordinates;
}
