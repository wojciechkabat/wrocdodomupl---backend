package com.kabat.petfinder.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aLostPet")
@Table(name = "lost_pets")
public class LostPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @NotNull
    private String name;
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
    @NotNull
    private Gender gender;
    @OneToMany
    @JoinColumn(name = "pet_id", referencedColumnName="id")
    private List<LostPetPicture> pictures;
    @Column(name = "last_seen")
    @NotNull
    private LocalDateTime lastSeen;
    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;
    @Embedded
    @NotNull
    private Coordinates coordinates;
}
