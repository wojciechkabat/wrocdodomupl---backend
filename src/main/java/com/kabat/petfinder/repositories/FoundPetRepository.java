package com.kabat.petfinder.repositories;

import com.kabat.petfinder.entities.FoundPet;
import com.kabat.petfinder.entities.LostPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoundPetRepository extends JpaRepository<FoundPet, Long> {
    @Query("select fp from FoundPet fp where fp.createdAt >= :creationDateTime")
    List<FoundPet> findAllWithCreationDateTimeAfter(@Param("creationDateTime") LocalDateTime creationDateTime);
}
