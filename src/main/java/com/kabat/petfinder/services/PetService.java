package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;

import java.util.List;

public interface PetService {
    List<PetDto> getLostPetsFromLast30Days();
    PetDto persistPet(PetDto petDto);
    List<PetDto> getFoundPetsFromLast30Days();
    List<PetDto> getAllPetsFromLast30Days();
}
