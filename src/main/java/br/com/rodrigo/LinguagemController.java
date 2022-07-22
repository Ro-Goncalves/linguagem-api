package br.com.rodrigo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinguagemController {
	
	@Autowired
	private LinguagemRepository repositorio;
	
	private List<Linguagem> linguagens = List.of(
		new Linguagem("Java", 
				      "https://raw.githubusercontent.com/abrahamcalf/programming-languages-logos/master/src/java/java_512x512.png", 
				      "8.7"),
		
		new Linguagem("PHP", 
				      "https://raw.githubusercontent.com/abrahamcalf/programming-languages-logos/master/src/php/php_512x512.png", 
				      "8.6")
	);
	
	@GetMapping("/linguagens")
	public List<Linguagem> obterLinguagens(){
		return this.linguagens;
	}
	
	 public int compare(String s1, String s2){
		 return s1.compareTo(s2);
    }	
	
	@GetMapping("/linguagens-repositorio")
	public List<Linguagem> obterLinguagensRepositorio(){	
		
		List<Linguagem> linguagensRepositorio = this.repositorio.findAll();
		Collections.sort(linguagensRepositorio, (a, b) -> a.getRanking().compareTo(b.getRanking()));
		
		return linguagensRepositorio;
	}	
	
	@PostMapping("/linguagens-repositorio")
	public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody PostDto postDto) {
		Linguagem linguagem = new Linguagem(postDto.titulo(), postDto.imagem(), postDto.rank());
		
		Linguagem linguagemSalva = repositorio.save(linguagem);
		
		return ResponseEntity.status(201).body(linguagemSalva);
	}
	
	@PostMapping("/linguagem-repositorio/votar")
	public ResponseEntity<Linguagem> cadastrarVoto(@RequestBody Voto voto){
		Voto cadastarVoto = new Voto(voto.id(), voto.voto());
		
		Linguagem linguagemEscolhida = repositorio.findById(cadastarVoto.id()).get();
		
		String votacaoAtual = linguagemEscolhida.getRanking();
		int votacaoNova = Integer.parseInt(votacaoAtual) + Integer.parseInt(cadastarVoto.voto());		
		linguagemEscolhida.setRanking("" + votacaoNova);
		
		return ResponseEntity.status(200).body(repositorio.save(linguagemEscolhida));
	}
	
	@GetMapping("/linguagens-repositorio/{id}")
	public Linguagem obterLinguagemRepositorio(@PathVariable String id) {
		Linguagem linguagemConsultada = repositorio.findById(id).get();
		
		return linguagemConsultada;		
	}
	
	@DeleteMapping("/linguagens-repositorio/{id}")
    public String deleteBook(@PathVariable String id){
		repositorio.deleteById(id);
        
        return "Deletado Com Sucesso";
    }
	
	@PatchMapping("/linguagens-repositorio/{id}/{ranking}")
	public Linguagem atualizarRankingLinguagem(@PathVariable String id, @PathVariable String ranking) {		
		Linguagem linguagem = repositorio.findById(id).get();
		linguagem.setRanking(ranking);
		
		Linguagem linguagemAtualizada = repositorio.save(linguagem);
		
		return linguagemAtualizada;		
	}	
	
	@PutMapping("/linguagens-repositorio/{id}")
	public Linguagem atualizarLinguagem(@PathVariable(value = "id") String id, @RequestBody Linguagem linguagem) {
		Linguagem linguagemNova = repositorio.findById(id).get();
		
		linguagemNova.setTitle(linguagem.getTitle());
		linguagemNova.setImage(linguagem.getImage());		
		linguagemNova.setRanking(linguagem.getRanking());	     
	     
	     final Linguagem linguagemAtualizada = repositorio.save(linguagemNova);
	     return linguagemAtualizada;
	}
}
