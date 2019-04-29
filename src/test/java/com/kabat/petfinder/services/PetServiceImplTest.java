package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.Pet;
import com.kabat.petfinder.entities.PetStatus;
import com.kabat.petfinder.entities.PetType;
import com.kabat.petfinder.repositories.PetRepository;
import com.kabat.petfinder.utils.PetMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.kabat.petfinder.dtos.CoordinatesDto.aCoordinatesDto;
import static com.kabat.petfinder.dtos.PetDto.aPetDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl lostPetService;

    @Captor
    private ArgumentCaptor<Pet> lostPetArgumentCaptor;

    @Test
    public void shouldPersistPet() {
        PetDto petDto = aPetDto()
                .name("DogName")
                .status(PetStatus.LOST)
                .type(PetType.DOG)
                .email("someemail")
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(petRepository.save(any())).thenReturn(PetMapper.mapToEntity(petDto));
        lostPetService.persistPet(petDto);
        verify(petRepository, times(1)).save(any());
    }

    @Test
    public void shouldAddCreatedAtDateOnPersisting() {
        PetDto petDto = aPetDto()
                .name("DogName")
                .type(PetType.DOG)
                .status(PetStatus.LOST)
                .email("someemail")
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(petRepository.save(any())).thenReturn(PetMapper.mapToEntity(petDto));

        lostPetService.persistPet(petDto);

        verify(petRepository).save(lostPetArgumentCaptor.capture());
        assertThat(lostPetArgumentCaptor.getValue().getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldAddCurrentDateAsLastSeenIfNotPresentOnPersisting() {
        PetDto petDto = aPetDto()
                .name("DogName")
                .type(PetType.DOG)
                .status(PetStatus.LOST)
                .email("someemail")
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(petRepository.save(any())).thenReturn(PetMapper.mapToEntity(petDto));

        lostPetService.persistPet(petDto);

        verify(petRepository).save(lostPetArgumentCaptor.capture());
        assertThat(lostPetArgumentCaptor.getValue().getLastSeen()).isNotNull();
        assertThat(lostPetArgumentCaptor.getValue().getLastSeen()).isEqualTo(lostPetArgumentCaptor.getValue().getCreatedAt());
    }
}