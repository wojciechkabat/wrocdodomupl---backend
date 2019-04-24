package com.kabat.petfinder.controllers;

import com.kabat.petfinder.dtos.LostPetDto;
import com.kabat.petfinder.services.LostPetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("pets/lost")
public class LostPetController {

    private final LostPetService lostPetService;

    public LostPetController(LostPetService lostPetService) {
        this.lostPetService = lostPetService;
    }

    @GetMapping()
    public List<LostPetDto> getPetsLostInTheLast30Days() {
        return lostPetService.getLostPetsFromLast30Days();
    }

    @PostMapping()
    public LostPetDto persistNewLostPet(@RequestBody LostPetDto lostPetDto) {
        return lostPetService.persistLostPet(lostPetDto);
    }
}
