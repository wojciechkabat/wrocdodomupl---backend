package com.kabat.petfinder.services;


import com.kabat.petfinder.entities.PetToken;

public interface EmailService {
    void sendPetConfirmationTokenEmail(String email, PetToken petToken);
}
