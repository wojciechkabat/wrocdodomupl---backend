package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.entities.*;
import com.kabat.petfinder.exceptions.IncorrectConfirmationTokenException;
import com.kabat.petfinder.exceptions.IncorrectTokenTypeException;
import com.kabat.petfinder.repositories.ConfirmTokenRepository;
import com.kabat.petfinder.repositories.PetRepository;
import com.kabat.petfinder.utils.PetMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.kabat.petfinder.dtos.CoordinatesDto.aCoordinatesDto;
import static com.kabat.petfinder.dtos.PetDto.aPetDto;
import static com.kabat.petfinder.entities.PetPicture.aPetPicture;
import static com.kabat.petfinder.entities.PetToken.aPetToken;
import static com.kabat.petfinder.entities.Pet.aPet;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceImplTest {
    @Mock
    private PetRepository petRepository;

    @Mock
    private ConfirmTokenRepository confirmTokenRepository;

    @Mock
    private PictureService pictureService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PetServiceImpl petService;

    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;

    @Captor
    private ArgumentCaptor<PetToken> petTokenArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<String>> picturesCaptor;

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
        verify(confirmTokenRepository, times(1)).save(petTokenArgumentCaptor.capture());

        assertThat(petTokenArgumentCaptor.getValue()).isNotNull();
        assertThat(petTokenArgumentCaptor.getValue().getTokenType()).isEqualTo(TokenType.CONFIRM);
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
                        aPetToken()
                                .token(confirmationToken)
                                .pet(pet)
                                .tokenType(TokenType.CONFIRM)
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
        PetToken petTokenEntity = aPetToken()
                .token(confirmationToken)
                .pet(pet)
                .tokenType(TokenType.CONFIRM)
                .build();
        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(Optional.of(petTokenEntity));
        petService.confirmPet(confirmationToken);
        verify(confirmTokenRepository, times(1)).delete(petTokenEntity);
    }

    @Test(expected = IncorrectTokenTypeException.class)
    public void shouldThrowExceptionWhenTryingToConfirmPetWithDELETEToken() {
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
        PetToken petTokenEntity = aPetToken()
                .token(confirmationToken)
                .pet(pet)
                .tokenType(TokenType.DELETE)
                .build();
        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(Optional.of(petTokenEntity));
        petService.confirmPet(confirmationToken);
    }

    @Test(expected = IncorrectTokenTypeException.class)
    public void shouldThrowExceptionWhenTryingToConfirmPetWithNULLToken() {
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
        PetToken petTokenEntity = aPetToken()
                .token(confirmationToken)
                .pet(pet)
                .tokenType(null)
                .build();
        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(Optional.of(petTokenEntity));
        petService.confirmPet(confirmationToken);
    }


    @Test
    public void shouldSendEmailWithConfirmationWhenPersistingPet() {
        UUID confirmationToken = UUID.fromString("0a9a6ef6-a47d-44d9-9d01-42c7d38d96fb");
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
        PetToken petTokenEntity = aPetToken()
                .token(confirmationToken)
                .pet(new Pet())
                .build();

        when(petRepository.save(any())).thenReturn(PetMapper.mapToEntity(petDto));
        when(confirmTokenRepository.save(any())).thenReturn(petTokenEntity);

        petService.persistPet(petDto);
        verify(emailService, times(1)).sendPetConfirmationTokenEmail("someemail", petTokenEntity);
    }

    @Test
    public void shouldCreateADeleteTokenWhenConfirmingPet() {
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
        PetToken confirmTokenEntity = aPetToken()
                .token(confirmationToken)
                .pet(pet)
                .tokenType(TokenType.CONFIRM)
                .build();
        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(Optional.of(confirmTokenEntity));
        petService.confirmPet(confirmationToken);
        verify(confirmTokenRepository, times(1)).save(petTokenArgumentCaptor.capture());

        assertThat(petTokenArgumentCaptor.getValue()).isNotNull();
        assertThat(petTokenArgumentCaptor.getValue().getTokenType()).isEqualTo(TokenType.DELETE);
    }

    @Test
    public void shouldSendEmailWithDeleteTokenWhenConfirmingPet() {
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
        PetToken petTokenEntity = aPetToken()
                .token(confirmationToken)
                .pet(pet)
                .tokenType(TokenType.CONFIRM)
                .build();

        when(confirmTokenRepository.findById(confirmationToken)).thenReturn(Optional.of(petTokenEntity));
        when(confirmTokenRepository.save(any())).thenReturn(petTokenEntity);

        petService.confirmPet(confirmationToken);
        verify(emailService, times(1)).sendPetDeleteTokenEmail("someemail", petTokenEntity);
    }

    @Test
    public void shouldCallAPIDeleteAllPicturesForAPet() {
        Pet pet = aPet()
                .pictures(
                        asList(
                                aPetPicture()
                                        .pictureId("pictureId1")
                                        .build(),
                                aPetPicture()
                                        .pictureId("pictureId2")
                                        .build(),
                                aPetPicture()
                                        .pictureId("pictureId3")
                                        .build()
                        )
                )
                .build();
        petService.deletePet(pet);
        verify(pictureService, times(1)).deleteFromRemoteServerByIds(picturesCaptor.capture());
        assertThat(picturesCaptor.getValue()).containsExactly("pictureId1", "pictureId2", "pictureId3");
    }
}