package org.generation.alicia.service;

import org.generation.alicia.model.Postagem;
import org.generation.alicia.repository.PostagemRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostagemService {

	
	// Injecao de dependencias
	@Autowired
	private PostagemRepository postagemRepository;
	
	// Métodos responsaveis pela contagem de curtidas
	public Postagem curtir(Long id) {
		
		Postagem postagem = buscarPostagemPeloId(id);
		
		postagem.setCurtidas(postagem.getCurtidas() + 1);
		
		return postagemRepository.save(postagem);
	}
	
	public Postagem descurtir(Long id) {
		
		Postagem postagem = buscarPostagemPeloId(id);
		
		if(postagem.getCurtidas() > 0) {
			postagem.setCurtidas(postagem.getCurtidas() - 1);
		} else {
			postagem.setCurtidas(0);
		}
		
		return postagemRepository.save(postagem);
	
	}
	
	private Postagem buscarPostagemPeloId(Long id) {
		
		Postagem postagemSalva = postagemRepository.findById(id).orElse(null);
		
		if(postagemSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return postagemSalva;
	}
	
}
