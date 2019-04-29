package com.kabat.petfinder.services;

import com.kabat.petfinder.entities.LostPet;
import com.kabat.petfinder.repositories.LostPetRepository;
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

    private final LostPetRepository lostPetRepository;

    public DataCleanUpService(LostPetRepository lostPetRepository) {
        this.lostPetRepository = lostPetRepository;
    }

    @Scheduled(cron = "0 0 1 ? * * *")
    public void cleanUpLostPets() {
        LOG.info("Commencing cleaning up of LOST pets: {}", dateFormat.format(new Date()));
        LocalDateTime nowMinus30Days = LocalDateTime.now().minusDays(30);
        List<LostPet> lostPetsOlderThan30Days = lostPetRepository.findAllWithCreationDateTimeBefore(nowMinus30Days);
        LOG.info("Number of LOST pets to delete: {}", lostPetsOlderThan30Days.size());

        for (LostPet petToDelete : lostPetsOlderThan30Days) {

        }
    }
}
