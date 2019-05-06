package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.entities.*;
import com.kabat.petfinder.exceptions.IncorrectConfirmationTokenException;
import com.kabat.petfinder.repositories.ConfirmTokenRepository;
import com.kabat.petfinder.repositories.PetRepository;
import com.kabat.petfinder.utils.PetMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.kabat.petfinder.dtos.CoordinatesDto.aCoordinatesDto;
import static com.kabat.petfinder.dtos.PetDto.aPetDto;
import static com.kabat.petfinder.entities.ConfirmToken.aConfirmToken;
import static com.kabat.petfinder.entities.Pet.aPet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @Mock
    private ConfirmTokenRepository confirmTokenRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;


    @Captor
    private ArgumentCaptor<ConfirmToken> confirmTokenArgumentCaptor;

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
        petService.persistPet(petDto);
        verify(petRepository, times(1)).save(any());
    }

    @Test
    public void shouldSetActiveToFalseWhenPersistingPet() {
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
        petService.persistPet(petDto);
        verify(petRepository, times(1)).save(petArgumentCaptor.capture());
        assertThat(petArgumentCaptor.getValue().isActive()).isFalse();
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

        petService.persistPet(petDto);

        verify(petRepository).save(petArgumentCaptor.capture());
        assertThat(petArgumentCaptor.getValue().getCreatedAt()).isNotNull();
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

        petService.persistPet(petDto);

        verify(petRepository).save(petArgumentCaptor.capture());
        assertThat(petArgumentCaptor.getValue().getLastSeen()).isNotNull();
        assertThat(petArgumentCaptor.getValue().getLastSeen()).isEqualTo(petArgumentCaptor.getValue().getCreatedAt());
    }

    @Test
    public void shouldCreateConfirmationTokenWhenPersisting() {
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

        petService.persistPet(petDto);
        verify(confirmTokenRepository, times(1)).save(confirmTokenArgumentCaptor.capture());

        assertThat(confirmTokenArgumentCaptor.getValue()).isNotNull();
    }

    @Test
    public void shouldSetActiveToTrueWhenConfirmingPet() {
        UUID confirmationToken = UUID.fromString("0a9a6ef6-a47d-44d9-9d01-42c7d38d96fb");
        Pet pet = aPet()
                .name("DogName")
                .type(PetType.DOG)
                .status(PetStatus.LOST)
                .email("someemail")
                .pictures(Collections.emptyList())
                .active(false)
                .coordinates(
                        Coordinates.aCoordinates()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(
                Optional.of(
                        aConfirmToken()
                                .token(confirmationToken)
                                .pet(pet)
                                .build()
                )
        );
        petService.confirmPet(confirmationToken);
        assertThat(pet.isActive()).isTrue();
    }

    @Test(expected = IncorrectConfirmationTokenException.class)
    public void shouldThrowExceptionWhenNoConfirmationTokenFound() {
        UUID confirmationToken = UUID.fromString("0a9a6ef6-a47d-44d9-9d01-42c7d38d96fb");
        petService.confirmPet(confirmationToken);
    }

    @Test
    public void shouldRemoveTokenWhenConfirmingPet() {
        UUID confirmationToken = UUID.fromString("0a9a6ef6-a47d-44d9-9d01-42c7d38d96fb");
        Pet pet = aPet()
                .name("DogName")
                .type(PetType.DOG)
                .status(PetStatus.LOST)
                .email("someemail")
                .pictures(Collections.emptyList())
                .active(false)
                .coordinates(
                        Coordinates.aCoordinates()
                                .longitude(BigDecimal.TEN)
                                .latitude(BigDecimal.ONE)
                                .build())
                .build();
        ConfirmToken confirmTokenEntity = aConfirmToken()
                .token(confirmationToken)
                .pet(pet)
                .build();
        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(Optional.of(confirmTokenEntity));
        petService.confirmPet(confirmationToken);
        verify(confirmTokenRepository, times(1)).delete(confirmTokenEntity);
    }
}