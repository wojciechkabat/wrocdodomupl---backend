package com.kabat.petfinder.services;

import com.kabat.petfinder.entities.Pet;
import com.kabat.petfinder.entities.PetPicture;
import com.kabat.petfinder.repositories.PetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.kabat.petfinder.entities.Pet.aPet;
import static com.kabat.petfinder.entities.PetPicture.aPetPicture;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataCleanUpServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetService petService;

    @Mock
    private PictureService pictureService;

    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;

    @InjectMocks
    private DataCleanUpService dataCleanUpService;

    @Test
    public void shouldDeletePetEntity() {
        UUID uuid1 = UUID.fromString("5441a5d3-0915-4648-97c2-07769fc78553");
        UUID uuid2 = UUID.fromString("5421a5d3-0915-4648-97c2-07769fc78552");

        when(petRepository.findAllWithCreationDateTimeBefore(any())).thenReturn(
                asList(
                        aPet()
                                .id(uuid1)
                                .pictures(
                                        singletonList(
                                                aPetPicture()
                                                        .pictureId("pictureId1")
                                                        .build()
                                        )
                                )
                                .build(),
                        aPet()
                                .id(uuid2)
                                .pictures(
                                        singletonList(
                                                aPetPicture()
                                                        .pictureId("pictureId4")
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        dataCleanUpService.cleanUpPets();
        verify(petService, times(2)).deletePet(petArgumentCaptor.capture());
        assertThat(petArgumentCaptor.getAllValues().get(0).getId()).isEqualTo(uuid1);
        assertThat(petArgumentCaptor.getAllValues().get(1).getId()).isEqualTo(uuid2);
    }
}