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
@Builder(builderMethodName = "aConfirmToken")
@Table(name = "confirm_tokens")
public class ConfirmToken {
    @Id
    @Column(name = "token")
    private UUID token;
    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
