package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.entities.Gender;
import com.kabat.petfinder.entities.LostPet;
import com.kabat.petfinder.entities.PetType;
import com.kabat.petfinder.repositories.LostPetRepository;
import com.kabat.petfinder.utils.LostPetMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.kabat.petfinder.dtos.CoordinatesDto.aCoordinatesDto;
import static com.kabat.petfinder.dtos.LostPetDto.aLostPetDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LostPetServiceImplTest {
    @Mock
    private LostPetRepository lostPetRepository;

    @InjectMocks
    private LostPetServiceImpl lostPetService;

    @Captor
    private ArgumentCaptor<LostPet> lostPetArgumentCaptor;

    @Test
    public void shouldPersistLostPet() {
        LostPetDto lostPetDto = aLostPetDto()
                .name("DogName")
                .type(PetType.DOG)
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(lostPetRepository.save(any())).thenReturn(LostPetMapper.mapToEntity(lostPetDto));
        lostPetService.persistLostPet(lostPetDto);
        verify(lostPetRepository, times(1)).save(any());
    }

    @Test
    public void shouldAddCreatedAtDateOnPersisting() {
        LostPetDto lostPetDto = aLostPetDto()
                .name("DogName")
                .type(PetType.DOG)
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(lostPetRepository.save(any())).thenReturn(LostPetMapper.mapToEntity(lostPetDto));

        lostPetService.persistLostPet(lostPetDto);

        verify(lostPetRepository).save(lostPetArgumentCaptor.capture());
        assertThat(lostPetArgumentCaptor.getValue().getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldAddCurrentDateAsLastSeenIfNotPresentOnPersisting() {
        LostPetDto lostPetDto = aLostPetDto()
                .name("DogName")
                .type(PetType.DOG)
                .gender(Gender.MALE)
                .coordinates(
                        aCoordinatesDto()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(lostPetRepository.save(any())).thenReturn(LostPetMapper.mapToEntity(lostPetDto));

        lostPetService.persistLostPet(lostPetDto);

        verify(lostPetRepository).save(lostPetArgumentCaptor.capture());
        assertThat(lostPetArgumentCaptor.getValue().getLastSeen()).isNotNull();
        assertThat(lostPetArgumentCaptor.getValue().getLastSeen()).isEqualTo(lostPetArgumentCaptor.getValue().getCreatedAt());
    }
}