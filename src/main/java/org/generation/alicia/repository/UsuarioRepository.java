package org.generation.alicia.repository;

import java.util.List;
import java.util.Optional;

import org.generation.alicia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);

	public Usuario findByNomeAndLogin(String nome, String login);
	
	public Optional<Usuario> findByLogin(String login);
}
