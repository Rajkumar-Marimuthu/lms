package com.rojatech.lms.service;

import reactor.core.publisher.Flux;

import java.util.List;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFluxMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() >3)
                .map(s -> s.length()+"-"+s);
    }

    public Flux<String> namesFluxFlatMap() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() >3)
                .flatMap(this::splitString);
    }

    public Flux<String> splitString(String s) {
        return Flux.fromArray(s.split(""));
    }
}
