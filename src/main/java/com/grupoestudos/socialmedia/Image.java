package com.grupoestudos.socialmedia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Image {

	private String id;
	private String name;

	public Image(int id, String name) {
		this.id = Integer.toString(id);
		this.name = name;
	}

	public Image(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
