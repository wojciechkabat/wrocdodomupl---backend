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

    @GetMapping("/lost")
    public List<PetDto> getPetsLostInTheLast30Days() {
        return petService.getLostPetsFromLast30Days();
    }

    @GetMapping("/found")
    public List<PetDto> getPetsFoundInTheLast30Days() {
        return petService.getFoundPetsFromLast30Days();
    }
}
