package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.FoundPetDto;
import com.kabat.petfinder.dtos.LostPetDto;

import java.util.List;

public interface FoundPetService {
    List<FoundPetDto> getFoundPetsFromLast30Days();
    FoundPetDto persistFoundPet(FoundPetDto foundPetDto);
}
