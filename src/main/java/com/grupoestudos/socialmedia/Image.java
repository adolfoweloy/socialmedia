package com.grupoestudos.socialmedia;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Image {

	@Id private String id;
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
