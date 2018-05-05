package com.grupoestudos.socialmedia.rest;

import com.grupoestudos.socialmedia.Image;
import com.grupoestudos.socialmedia.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);

	private final ImageService imageService;

	public ApiController(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping("/images")
	public Flux<Image> images() {
		return imageService.findAllImages();
	}

	@PostMapping("/images")
	Mono<Void> save(@RequestBody Flux<Image> images) {
		return images
		.map(image -> {
			log.info("an image will be saved " + image);
			return image;
		}).then();
	}
}
