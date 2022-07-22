package br.com.rodrigo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "principaisLinguagens")
public class Linguagem {
	
	@Id
	private String id;	
	
	private String title;
	private String image;
	private String ranking;
	
	public Linguagem() {
		
	}
	
	public Linguagem(String title, String image, String ranking) {
		this.title = title;
		this.image = image;
		this.ranking = ranking;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getRanking() {
		return ranking;
	}
	
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

}
