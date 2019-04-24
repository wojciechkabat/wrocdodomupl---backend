package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.entities.LostPet;
import com.kabat.petfinder.repositories.LostPetRepository;
import com.kabat.petfinder.utils.LostPetMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LostPetServiceImpl implements LostPetService {

    private final LostPetRepository lostPetRepository;

    public LostPetServiceImpl(LostPetRepository lostPetRepository) {
        this.lostPetRepository = lostPetRepository;
    }

    @Override
    public List<LostPetDto> getLostPetsFromLast30Days() {
        return lostPetRepository.findAllWithCreationDateTimeAfter(LocalDateTime.now().minusDays(30))
                .stream()
                .map(LostPetMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LostPetDto persistLostPet(LostPetDto lostPetDto) {
        LostPet savedPet = lostPetRepository.save(LostPetMapper.mapToEntity(lostPetDto));
        return LostPetMapper.mapToDto(savedPet);
    }
}
