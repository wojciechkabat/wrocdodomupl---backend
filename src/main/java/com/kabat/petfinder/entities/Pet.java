package com.kabat.petfinder.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "aPet")
@Table(name = "pets")
public class Pet {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "additionalInfo")
    private String additionalInfo;
    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "email")
    @NotNull
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PetStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull
    private PetType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id", referencedColumnName="id")
    private List<PetPicture> pictures;
    @Column(name = "last_seen")
    @NotNull
    private LocalDateTime lastSeen;
    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;
    @Embedded
    @NotNull
    private Coordinates coordinates;
    @Column(name = "active")
    @NotNull
    private boolean active;
}
