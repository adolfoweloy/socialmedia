package com.grupoestudos.socialmedia;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest(
//		excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class
)
public class ImageServiceTests {

	@Autowired
	ImageRepository repository;

	@Autowired
	MongoOperations operations;

	@Before
	public void setUp() {
		operations.dropCollection(Image.class);
		operations.insert(new Image("1", "learning-spring-boot-cover.jpg"));
		operations.insert(new Image("2", "oauth 2.0 cookbook with spring security.jpg"));
		operations.insert(new Image("3", "bazinga.jpg"));
		operations.findAll(Image.class).forEach(System.out::println);
	}

	@Test
	public void findAllShouldWork() {
		Flux<Image> images = repository.findAll();
		StepVerifier.create(images)
				.recordWith(ArrayList::new) // fetches entire Flux and converts it to an array
				.expectNextCount(3)
				.consumeRecordedWith(results -> {
					assertThat(results).hasSize(3);
					assertThat(results)
						.extracting(Image::getName)
						.contains(
								"learning-spring-boot-cover.jpg",
								"oauth 2.0 cookbook with spring security.jpg",
								"bazinga.jpg");
				})
				.expectComplete()
				.verify();
	}

	@Test
	public void findByNameShouldWork() {
		Mono<Image> image = repository.findByName("bazinga.jpg");
		StepVerifier.create(image)
				.expectNextMatches(results -> {
					assertThat(results.getName()).isEqualTo("bazinga.jpg");
					assertThat(results.getId()).isEqualTo("3");
					return true;
				}).verifyComplete();
	}
}