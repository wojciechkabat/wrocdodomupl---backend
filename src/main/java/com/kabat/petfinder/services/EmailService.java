package com.kabat.petfinder.services;


import com.kabat.petfinder.entities.ConfirmToken;

public interface EmailService {
    void sendPetConfirmationTokenEmail(String email, ConfirmToken confirmToken);
}
