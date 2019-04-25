package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.FoundPetDto;
import com.kabat.petfinder.entities.FoundPet;
import com.kabat.petfinder.repositories.FoundPetRepository;
import com.kabat.petfinder.utils.FoundPetMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoundPetServiceImpl implements FoundPetService {
    private final FoundPetRepository foundPetRepository;

    public FoundPetServiceImpl(FoundPetRepository foundPetRepository) {
        this.foundPetRepository = foundPetRepository;
    }

    @Override
    public List<FoundPetDto> getFoundPetsFromLast30Days() {
        return foundPetRepository.findAllWithCreationDateTimeAfter(LocalDateTime.now().minusDays(30))
                .stream()
                .map(FoundPetMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoundPetDto persistFoundPet(FoundPetDto foundPetDto) {
        FoundPet savedPet = foundPetRepository.save(FoundPetMapper.mapToEntity(foundPetDto));
        return FoundPetMapper.mapToDto(savedPet);
    }
}
