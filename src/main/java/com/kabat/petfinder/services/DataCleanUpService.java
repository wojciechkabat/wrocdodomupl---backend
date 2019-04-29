package com.kabat.petfinder.services;

import com.kabat.petfinder.entities.Pet;
import com.kabat.petfinder.repositories.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class DataCleanUpService {
    private static final Logger LOG = LoggerFactory.getLogger(DataCleanUpService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final PetRepository petRepository;

    public DataCleanUpService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Scheduled(cron = "0 0 0 25 12 ?")
    public void cleanUpLostPets() {
        LOG.info("Commencing cleaning up of LOST pets: {}", dateFormat.format(new Date()));
        LocalDateTime nowMinus30Days = LocalDateTime.now().minusDays(30);
        List<Pet> petsOlderThan30Days = petRepository.findAllWithCreationDateTimeBefore(nowMinus30Days);
        LOG.info("Number of LOST pets to delete: {}", petsOlderThan30Days.size());

        for (Pet petToDelete : petsOlderThan30Days) {

        }
    }
}
