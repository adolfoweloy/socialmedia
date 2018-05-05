package com.grupoestudos.socialmedia;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

	private final ImageService imageService;

	public HomeController(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping("/")
	public Mono<String> index(Model model) {
		model.addAttribute("images", imageService.findAllImages());
		return Mono.just("index");
	}

}
