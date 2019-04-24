package com.kabat.petfinder.controllers;

import com.kabat.petfinder.dtos.LostPetDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pets/lost")
public class LostPetController {

    @GetMapping()
    public LostPetDto test() {
        return LostPetDto.aLostPetDto().name("misiek").build();
    }
}
