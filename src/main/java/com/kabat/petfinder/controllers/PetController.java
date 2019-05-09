package com.kabat.petfinder.controllers;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.services.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("")
    public PetDto persistNewPet(@RequestBody PetDto petDto) {
        return petService.persistPet(petDto);
    }

    @GetMapping()
    public List<PetDto> getAllActivePetsFromLast30Days() {
        return petService.getAllActivePetsFromLast30Days();
    }

    @PutMapping("confirmation")
    public PetDto confirmPet(@RequestParam("token") UUID confirmationToken) {
        return petService.confirmPet(confirmationToken);
    }

    @DeleteMapping("delete")
    public void deletePet(@RequestParam("token") UUID deleteToken) {
        petService.deletePet(deleteToken);
    }
}
