package br.com.rodrigo;

import org.springframework.data.annotation.Id;

public record PostDto (
	
	@Id
	String id,
		
	String titulo,
	String imagem,
	String rank
) {}
