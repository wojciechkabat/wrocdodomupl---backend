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
@Builder(builderMethodName = "aLostPetPicture")
@Table(name = "lost_pet_pictures")
public class LostPetPicture {
    @Id
    @Column(name = "picture_id")
    private String pictureId;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "pet_id")
    private Long petId;
}
