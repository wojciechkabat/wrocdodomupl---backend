package com.kabat.petfinder.repositories;

import com.kabat.petfinder.entities.LostPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LostPetRepository extends JpaRepository<LostPet, Long> {
    @Query("select lp from LostPet lp where lp.createdAt >= :creationDateTime")
    List<LostPet> findAllWithCreationDateTimeAfter(@Param("creationDateTime") LocalDateTime creationDateTime);

    @Query("select lp from LostPet lp where lp.createdAt < :creationDateTime")
    List<LostPet> findAllWithCreationDateTimeBefore(@Param("creationDateTime") LocalDateTime creationDateTime);
}
