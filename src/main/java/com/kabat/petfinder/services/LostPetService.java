package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.LostPetDto;

import java.util.List;

public interface LostPetService {
    List<LostPetDto> getLostPetsFromLast30Days();
    LostPetDto persistLostPet(LostPetDto lostPetDto);
}
