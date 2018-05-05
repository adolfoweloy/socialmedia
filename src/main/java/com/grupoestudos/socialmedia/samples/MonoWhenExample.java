package com.grupoestudos.socialmedia.samples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

public class MonoWhenExample {

	public static void main(String[] args) {
		Flux<String> items = Flux.just("a", "b", "c");

		Function<String, String> funcString = (text) -> {
			return "hello " + text;
		};

		Supplier<Integer> supInt = () -> {
			return 10 / 0;
		};

		// se ao invés de usar o flatMap, usássemos o map,
		// o subscribe seria executado para cada elemento da string.
		// nesse caso, queremos um resultado Mono<Void> que se trata de apenas um valor
		// que por sua vez é um MonoWhen.
		items.flatMap(elem -> {
			Mono<String> string = Mono.just(funcString.apply(elem));
			Mono<Integer> number = Mono.fromSupplier(supInt);

			// ao usar o mono, um novo mono será criado agregando
			// os monos passados como argumento.
			// O mono de agregação só será retornado quanto todos os monos
			// passados como argumento completarem suas execuções.
			return Mono.when(string, number);
		})
		.doOnError(err -> {
			System.out.println(err.getMessage());
		})
		.subscribe();
	}
}
