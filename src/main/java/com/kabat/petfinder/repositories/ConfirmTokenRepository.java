package com.kabat.petfinder.repositories;

import com.kabat.petfinder.entities.PetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<PetToken, UUID> {
}
