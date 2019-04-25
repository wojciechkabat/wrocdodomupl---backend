package com.kabat.petfinder.controllers;

import com.kabat.petfinder.dtos.FoundPetDto;
import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.services.FoundPetService;
import com.kabat.petfinder.services.LostPetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("pets")
public class PetController {

    private final LostPetService lostPetService;
    private final FoundPetService foundPetService;

    public PetController(LostPetService lostPetService, FoundPetService foundPetService) {
        this.lostPetService = lostPetService;
        this.foundPetService = foundPetService;
    }

    @GetMapping("/lost")
    public List<LostPetDto> getPetsLostInTheLast30Days() {
        return lostPetService.getLostPetsFromLast30Days();
    }

    @PostMapping("/lost")
    public LostPetDto persistNewLostPet(@RequestBody LostPetDto lostPetDto) {
        return lostPetService.persistLostPet(lostPetDto);
    }

    @GetMapping("/found")
    public List<FoundPetDto> getPetsFoundInTheLast30Days() {
        return foundPetService.getFoundPetsFromLast30Days();
    }

    @PostMapping("/found")
    public FoundPetDto persistNewFoundPet(@RequestBody FoundPetDto foundPetDto) {
        return foundPetService.persistFoundPet(foundPetDto);
    }
}
