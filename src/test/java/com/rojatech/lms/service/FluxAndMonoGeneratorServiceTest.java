package com.rojatech.lms.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

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

    @Test
    void namesFluxFlatMapAsync() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxFlatMapAsync();
        StepVerifier.create(namesFlux)
                //.expectNext("A","L","E","X","C","H","L","O","E")
        .expectNextCount(9).verifyComplete();
    }

    @Test
    void namesFluxConcatMapAsync() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxConcatMapAsync();
        StepVerifier.create(namesFlux).expectNext("A","L","E","X","C","H","L","O","E").verifyComplete();
    }

    @Test
    void namesMonoFlatMap() {
        var nameMono = fluxAndMonoGeneratorService.namesMonoFlatMap();
        StepVerifier.create(nameMono).expectNext(List.of("A","L","E","X")).verifyComplete();
    }

    @Test
    void namesMonoFlatMapMany() {
        var nameFlux = fluxAndMonoGeneratorService.namesMonoFlatMapMany();
        StepVerifier.create(nameFlux).expectNext("A","L","E","X").verifyComplete();
    }

    @Test
    void namesFluxTransform() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransform();
        StepVerifier.create(namesFlux).expectNext("A","L","E","X","C","H","L","O","E").verifyComplete();
    }

    @Test
    void namesFluxTransformDefaultIfEmpty() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransformDefaultIfEmpty();
        StepVerifier.create(namesFlux).expectNext("D","E","F","A","U","L","T").verifyComplete();
    }

    @Test
    void namesFluxTransformSwitchIfEmpty() {
        var namesFlux = fluxAndMonoGeneratorService.namesFluxTransformSwitchIfEmpty();
        StepVerifier.create(namesFlux).expectNext("D","E","F","A","U","L","T").verifyComplete();
    }

    @Test
    void exploreConcat() {
        var concatFlux = fluxAndMonoGeneratorService.exploreConcat();
        StepVerifier.create(concatFlux).expectNext("A","B","C","D","E","F").verifyComplete();
    }

    @Test
    void exploreConcatWithMono() {
        var concatFlux = fluxAndMonoGeneratorService.exploreConcatWithMono();
        StepVerifier.create(concatFlux).expectNext("A","B","C","D").verifyComplete();
    }

    @Test
    void exploreConcatWithFlux() {
        var concatFlux = fluxAndMonoGeneratorService.exploreConcatWithFlux();
        StepVerifier.create(concatFlux).expectNext("A","B","C","D","E","F").verifyComplete();
    }

    @Test
    void exploreMerge() {
        var mergeFlux = fluxAndMonoGeneratorService.exploreMerge();
        StepVerifier.create(mergeFlux).expectNext("A","D", "B", "E", "C", "F").verifyComplete();
    }

    @Test
    void exploreMergeWith() {
        var mergeFlux = fluxAndMonoGeneratorService.exploreMergeWith();
        StepVerifier.create(mergeFlux).expectNext("A","D", "B", "E", "C", "F").verifyComplete();
    }

    @Test
    void exploreMergeWithMono() {
        var mergeFlux = fluxAndMonoGeneratorService.exploreMergeWithMono();
        StepVerifier.create(mergeFlux).expectNext("A","D", "E","F").verifyComplete();
    }

    @Test
    void exploreMergeSequential() {
        var concatFlux = fluxAndMonoGeneratorService.exploreMergeSequential();
        StepVerifier.create(concatFlux).expectNext("A","B","C","D","E","F").verifyComplete();
    }

    @Test
    void exploreZip() {
        var concatFlux = fluxAndMonoGeneratorService.exploreZip();
        StepVerifier.create(concatFlux).expectNext("AD14","BE25","CF36").verifyComplete();
    }

    @Test
    void exploreZipWith() {
        var concatFlux = fluxAndMonoGeneratorService.exploreZipWith();
        StepVerifier.create(concatFlux).expectNext("AD","BE","CF").verifyComplete();
    }

    @Test
    void exploreZipWithMono() {
        var concatFlux = fluxAndMonoGeneratorService.exploreZipWithMono();
        StepVerifier.create(concatFlux).expectNext("AD").verifyComplete();
    }
}