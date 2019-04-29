package com.kabat.petfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PetFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetFinderApplication.class, args);
    }

}
