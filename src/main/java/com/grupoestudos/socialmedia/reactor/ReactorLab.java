package com.grupoestudos.socialmedia.reactor;


import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class ReactorLab {

	public static void main(String[] args) {

		Flux.just("alpha", "bravo", "charlie")
			.map(String::toUpperCase)
			.flatMap(s -> Flux.fromArray(s.split("")))
			.groupBy(c -> c)
			.sort(Comparator.comparing(GroupedFlux::key))
			.flatMap(g -> Mono.just(g.key()).zipWith(g.count()))
				.map(keyAndCount -> keyAndCount.getT1() + " => " + keyAndCount.getT2())
			.subscribe(System.out::println);

		// does not execute getSomething until subscribe is invoked
		Mono<String> supplierMono = Mono.fromSupplier(() -> getSomething());
		System.out.println("before subscribing");
		supplierMono.subscribe((res) -> {
			System.out.println(res);
		});

		System.out.println();

		// executes getSomething before subscribe is invoked
		Mono<String> monoString = Mono.just(getSomething());
		System.out.println("before subscribing");
		monoString.subscribe((res) -> {
			System.out.println(res);
		});
	}

	private static String getSomething() {
		System.out.println("getSomething");
		return "sample";
	}
}
