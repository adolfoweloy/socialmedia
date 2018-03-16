package com.grupoestudos.socialmedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class ImagesController {

	private Logger log = LoggerFactory.getLogger(ImagesController.class);

	private static final String BASE_PATH = "/images";
	private static final String FILENAME = "{filename:.+}";

	private final ImageService imageService;

	public ImagesController(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping("/api/images")
	Flux<Image> images() {
		return Flux.just(
				new Image("1", "demigreite.jpg"),
				new Image("2", "nepomuceno.jpg"),
				new Image("3", "piteco.jpg")
		);
	}

	@PostMapping("/api/images")
	Mono<Void> create(@RequestBody Flux<Image> images) {
		return images
				.map(image -> {
					log.info("we will save " + image.getId() + " to a reactive database soon!");
					return image;
				}).then(); // will return a Mono when the current Flux completes
	}




	@GetMapping(value = BASE_PATH + "/" + FILENAME + "/raw",
			produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public Mono<ResponseEntity<?>> oneRawImage(@PathVariable String filename) {
		return imageService.findOneImage(filename)
			.map(resource -> {
				try {
					return ResponseEntity.ok()
						.contentLength(resource.contentLength())
						.body(new InputStreamResource(
								resource.getInputStream()));
				} catch (IOException e) {
					return ResponseEntity.badRequest()
						.body("Couldn't find " + filename +
								" => " + e.getMessage());
				}
			});
	}


	@PostMapping(value = BASE_PATH)
	public Mono<String> createFile(@RequestPart(name = "file") Flux<FilePart> files) {
		return imageService.createImage(files)
				.then(Mono.just("redirect:/"));
	}

	@DeleteMapping(BASE_PATH + "/" + FILENAME)
	public Mono<String> deleteFile(@PathVariable String filename) {
		return imageService.deleteImage(filename)
				.then(Mono.just("redirect:/"));
	}
}
