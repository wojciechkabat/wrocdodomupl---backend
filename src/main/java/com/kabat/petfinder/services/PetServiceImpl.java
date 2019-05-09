package com.kabat.petfinder.services;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.entities.PetToken;
import com.kabat.petfinder.entities.Pet;
import com.kabat.petfinder.entities.TokenType;
import com.kabat.petfinder.exceptions.IncorrectConfirmationTokenException;
import com.kabat.petfinder.exceptions.IncorrectTokenTypeException;
import com.kabat.petfinder.repositories.ConfirmTokenRepository;
import com.kabat.petfinder.repositories.PetRepository;
import com.kabat.petfinder.utils.PetMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ConfirmTokenRepository confirmTokenRepository;
    private final EmailService emailService;

    public PetServiceImpl(PetRepository petRepository, ConfirmTokenRepository confirmTokenRepository, EmailService emailService) {
        this.petRepository = petRepository;
        this.confirmTokenRepository = confirmTokenRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public PetDto persistPet(PetDto petDto) {
        Pet petEntity = PetMapper.mapToEntity(petDto);
        LocalDateTime currentLocalDate = LocalDateTime.now();
        petEntity.setCreatedAt(currentLocalDate);
        petEntity.setId(UUID.randomUUID());
        petEntity.getPictures().forEach(picture -> picture.setPetId(petEntity.getId()));
        petEntity.setActive(false);

        if (petDto.getLastSeen() == null) {
            petEntity.setLastSeen(currentLocalDate);
        }
        Pet savedPet = petRepository.save(petEntity);

        PetToken confirmationToken = createAndStoreToken(savedPet, TokenType.CONFIRM);
        emailService.sendPetConfirmationTokenEmail(petDto.getEmail(), confirmationToken);

        return PetMapper.mapToDto(savedPet);
    }

    @Override
    public List<PetDto> getAllActivePetsFromLast30Days() {
        return petRepository.findAllActiveWithCreationDateTimeAfter(LocalDateTime.now().minusDays(30))
                .stream()
                .map(PetMapper::mapToDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public PetDto confirmPet(UUID confirmToken) {
        PetToken confirmationTokenEntity = confirmTokenRepository.findById(confirmToken)
                .orElseThrow(() -> new IncorrectConfirmationTokenException(
                        String.format("No confirmation token found with id: %s", confirmToken)
                ));
        if (!TokenType.CONFIRM.equals(confirmationTokenEntity.getTokenType())) {
           throw new IncorrectTokenTypeException("The found token type was not of type CONFIRM");
        }

        Pet associatedPet = confirmationTokenEntity.getPet();
        associatedPet.setActive(true);
        confirmTokenRepository.delete(confirmationTokenEntity);

        PetToken deleteToken = createAndStoreToken(associatedPet, TokenType.DELETE);
        emailService.sendPetDeleteTokenEmail(associatedPet.getEmail(), deleteToken);

        return PetMapper.mapToDto(associatedPet);
    }

    private PetToken createAndStoreToken(Pet pet, TokenType tokenType) {
        PetToken petToken = PetToken.aPetToken()
                .token(UUID.randomUUID())
                .pet(pet)
                .tokenType(tokenType)
                .build();
        return confirmTokenRepository.save(petToken);
    }
}
