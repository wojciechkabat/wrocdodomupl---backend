package com.kabat.petfinder.controllers;

import com.kabat.petfinder.dtos.PetDto;
import com.kabat.petfinder.services.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<PetDto> getAllPetsFromLast30Days() {
        return petService.getAllPetsFromLast30Days();
    }
}
