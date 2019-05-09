package com.kabat.petfinder.services;

import com.kabat.petfinder.entities.Pet;
import com.kabat.petfinder.entities.PetPicture;
import com.kabat.petfinder.repositories.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DataCleanUpService {
    private static final Logger LOG = LoggerFactory.getLogger(DataCleanUpService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final PetRepository petRepository;
    private final PetService petService;
    private final PictureService pictureService;

    public DataCleanUpService(PetRepository petRepository, PetService petService, PictureService pictureService) {
        this.petRepository = petRepository;
        this.petService = petService;
        this.pictureService = pictureService;
    }

    @Scheduled(cron = "0 1 1 * * ?")
    @Transactional
    public void cleanUpPets() {
        LOG.info("===================== CLEAN UP STARTING =====================");
        LOG.info("Commencing cleaning up of pets: {}", dateFormat.format(new Date()));
        LocalDateTime nowMinus30Days = LocalDateTime.now().minusDays(30);
        List<Pet> petsOlderThan30Days = petRepository.findAllWithCreationDateTimeBefore(nowMinus30Days);
        LOG.info("Number of pets to delete: {}", petsOlderThan30Days.size());

        for (Pet petToDelete : petsOlderThan30Days) {
            petService.deletePet(petToDelete);
            LOG.info("Successfully deleted pet: {}", petToDelete.getId());
        }

        LOG.info("Successfully deleted pets: {}", petsOlderThan30Days.size());
        LOG.info("===================== CLEAN UP FINISHED =====================");
    }
}
