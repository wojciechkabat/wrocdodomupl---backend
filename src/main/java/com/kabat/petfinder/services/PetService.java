package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;

import java.util.List;
import java.util.UUID;

public interface PetService {
    PetDto persistPet(PetDto petDto);
    List<PetDto> getAllActivePetsFromLast30Days();
    PetDto confirmPet(UUID confirmToken);
}
