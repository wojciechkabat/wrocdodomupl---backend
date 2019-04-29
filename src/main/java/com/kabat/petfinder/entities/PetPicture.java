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
@Builder(builderMethodName = "aPetPicture")
@Table(name = "pet_pictures")
public class PetPicture {
    @Id
    @Column(name = "picture_id")
    private String pictureId;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "pet_id")
    private UUID petId;
}
