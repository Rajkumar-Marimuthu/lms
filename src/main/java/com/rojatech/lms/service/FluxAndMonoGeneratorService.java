package com.rojatech.lms.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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

    public Flux<String> namesFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() >3)
                .flatMap(this::splitStringWithDelay);
    }

    public Flux<String> namesFluxConcatMapAsync() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() >3)
                .concatMap(this::splitStringWithDelay);
    }
    private Flux<String> splitString(String s) {
        return Flux.fromArray(s.split(""));
    }

    private Flux<String> splitStringWithDelay(String s) {
        return Flux.fromArray(s.split("")).delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }

    public Mono<List<String>> namesMonoFlatMap() {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() >3)
                .flatMap(this::splitStringMono);
    }

    public Flux<String> namesMonoFlatMapMany() {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() >3)
                .flatMapMany(this::splitString);
    }

    private Mono<List<String>> splitStringMono(String s) {
        return Mono.just(List.of(s.split("")));
    }

    public Flux<String> namesFluxTransform() {
        Function<Flux<String>, Flux<String>> filterMap =  name -> name.map(String::toUpperCase)
                .filter(s -> s.length() >3);

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .flatMap(this::splitString);
    }

    public Flux<String> namesFluxTransformDefaultIfEmpty() {
        Function<Flux<String>, Flux<String>> filterMap =  name -> name.map(String::toUpperCase)
                .filter(s -> s.length() >6);

        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .defaultIfEmpty("DEFAULT")
                .flatMap(this::splitString);
    }

    public Flux<String> namesFluxTransformSwitchIfEmpty() {
        Function<Flux<String>, Flux<String>> filterMap =  name -> name.map(String::toUpperCase)
                .filter(s -> s.length() >6)
                .flatMap(this::splitString);

        var defaultFlux = Flux.just("default").transform(filterMap);
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .switchIfEmpty(defaultFlux);
    }

    public Flux<String> exploreConcat() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");
        return Flux.concat(flux1,flux2);
    }

    public Flux<String> exploreConcatWithMono() {
        var flux1 = Flux.just("A", "B", "C");
        var mono2 = Mono.just("D");
        return Flux.concat(flux1,mono2);
    }

    public Flux<String> exploreConcatWithFlux() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");
        return flux1.concatWith(flux2);
    }

    public Flux<String> exploreMerge() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return Flux.merge(flux1,flux2);
    }

    public Flux<String> exploreMergeWith() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return flux1.mergeWith(flux2);
    }

    public Flux<String> exploreMergeWithMono() {
        var mono1 = Mono.just("A");
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return mono1.mergeWith(flux2);
    }

    public Flux<String> exploreMergeSequential() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(flux1,flux2);
    }

    public Flux<String> exploreZip() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");
        var flux3 = Flux.just("1", "2", "3");
        var flux4 = Flux.just("4", "5", "6");
        return Flux.zip(flux1,flux2,flux3,flux4).map(t -> t.getT1() + t.getT2() + t.getT3() + t.getT4());
    }

    public Flux<String> exploreZipWith() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");
        return flux1.zipWith(flux2,(a,b)->(a+b));
    }

    public Mono<String> exploreZipWithMono() {
        var mono1 = Mono.just("A");
        var mono2 = Mono.just("D");
        return mono1.zipWith(mono2).map(t -> t.getT1() +t.getT2());
    }

}
