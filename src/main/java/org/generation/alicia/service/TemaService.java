package org.generation.alicia.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.generation.alicia.model.Tema;
import org.generation.alicia.repository.PostagemRepository;
import org.generation.alicia.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemaService {

	// Injecao de Dependencias
	
	@Autowired
	private TemaRepository temaRepository;
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	// Método trendingTopics
	
	public List<Tema> trendingTopics() {
		
		List<Tema> temas = temaRepository.findAll();
		
		for(Tema tema: temas) {
			
			tema.setQtdPostagem(postagemRepository.countPosts(tema.getId()));
			
		}
		
		Collections.sort(temas, Collections.reverseOrder(Comparator.comparing(Tema::getQtdPostagem)));
		
		return temas;
		
	}
	
}
