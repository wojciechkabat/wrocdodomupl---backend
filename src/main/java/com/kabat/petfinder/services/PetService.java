package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.entities.Pet;

import java.util.List;
import java.util.UUID;

public interface PetService {
    PetDto persistPet(PetDto petDto);
    List<PetDto> getAllActivePetsFromLast30Days();
    PetDto confirmPet(UUID confirmToken);
    PetDto deletePet(UUID deleteToken);
    void deletePet(Pet pet);
}
