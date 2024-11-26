package com.rojatech.lms.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
    @Test
    void namesFluxMap() {

        var namesFlux = fluxAndMonoGeneratorService.namesFluxMap();
        StepVerifier.create(namesFlux).expectNext("4-ALEX", "5-CHLOE").verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMap();
        StepVerifier.create(namesFlux).expectNext("A","L","E","X","C","H","L","O","E").verifyComplete();
    }
}