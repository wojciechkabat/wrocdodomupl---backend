package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.FoundPetDto;
import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.entities.FoundPet;
import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.LostPet;
import com.kabat.petfinder.entities.PetType;
import com.kabat.petfinder.repositories.FoundPetRepository;
import com.kabat.petfinder.repositories.LostPetRepository;
import com.kabat.petfinder.utils.FoundPetMapper;
import com.kabat.petfinder.utils.LostPetMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.kabat.petfinder.dtos.CoordinatesDto.aCoordinatesDto;
import static com.kabat.petfinder.dtos.FoundPetDto.aFoundPetDto;
import static com.kabat.petfinder.dtos.LostPetDto.aLostPetDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FoundPetServiceImplTest {
    @Mock
    private FoundPetRepository foundPetRepository;

    @InjectMocks
    private FoundPetServiceImpl foundPetService;

    @Captor
    private ArgumentCaptor<FoundPet> foundPetArgumentCaptor;

    @Test
    public void shouldPersistLostPet() {
        FoundPetDto foundPetDto = aFoundPetDto()
                .type(PetType.DOG)
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(foundPetRepository.save(any())).thenReturn(FoundPetMapper.mapToEntity(foundPetDto));
        foundPetService.persistFoundPet(foundPetDto);
        verify(foundPetRepository, times(1)).save(any());
    }

    @Test
    public void shouldAddCreatedAtDateOnPersisting() {
        FoundPetDto foundPetDto = aFoundPetDto()
                .type(PetType.DOG)
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(foundPetRepository.save(any())).thenReturn(FoundPetMapper.mapToEntity(foundPetDto));

        foundPetService.persistFoundPet(foundPetDto);

        verify(foundPetRepository).save(foundPetArgumentCaptor.capture());
        assertThat(foundPetArgumentCaptor.getValue().getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldAddCurrentDateAsWhenSeenIfNotPresentOnPersisting() {
        FoundPetDto foundPetDto = aFoundPetDto()
                .type(PetType.DOG)
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(foundPetRepository.save(any())).thenReturn(FoundPetMapper.mapToEntity(foundPetDto));

        foundPetService.persistFoundPet(foundPetDto);

        verify(foundPetRepository).save(foundPetArgumentCaptor.capture());
        assertThat(foundPetArgumentCaptor.getValue().getWhenSeen()).isNotNull();
        assertThat(foundPetArgumentCaptor.getValue().getWhenSeen()).isEqualTo(foundPetArgumentCaptor.getValue().getCreatedAt());
    }
}