package com.grupoestudos.socialmedia.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorLab {

	public static void main(String[] args) {
		Flux.just("alpha", "bravo", "charlie")
				.map(String::toUpperCase) // ALPHA, BRAVO, CHARLIE
				.flatMap(s -> Flux.fromArray(s.split("")))  // ALPHABRAVOCHARLIE (map elements creating arrays of chars, then concatenate results
				.groupBy(String::toString)
				.flatMap(group -> Mono.just(group.key()).and(group.count()))
//				.map(keyAndCount -> keyAndCount.getT1() + " => " + keyAndCount.getT2()) // does not work :(
				.subscribe(System.out::print);
	}
}
