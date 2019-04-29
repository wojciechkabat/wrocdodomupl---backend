package com.kabat.petfinder.repositories;

import com.kabat.petfinder.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    @Query("select lp from Pet lp where lp.status = com.kabat.petfinder.entities.PetStatus.LOST AND lp.createdAt >= :creationDateTime")
    List<Pet> findAllLostWithCreationDateTimeAfter(@Param("creationDateTime") LocalDateTime creationDateTime);

    @Query("select lp from Pet lp where lp.status = com.kabat.petfinder.entities.PetStatus.FOUND AND lp.createdAt >= :creationDateTime")
    List<Pet> findAllFoundWithCreationDateTimeAfter(@Param("creationDateTime") LocalDateTime creationDateTime);

    @Query("select lp from Pet lp where lp.createdAt < :creationDateTime")
    List<Pet> findAllWithCreationDateTimeBefore(@Param("creationDateTime") LocalDateTime creationDateTime);
}
