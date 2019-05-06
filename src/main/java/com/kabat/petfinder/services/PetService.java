package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;

import java.util.List;

public interface PetService {
    PetDto persistPet(PetDto petDto);
    List<PetDto> getAllPetsFromLast30Days();
}
