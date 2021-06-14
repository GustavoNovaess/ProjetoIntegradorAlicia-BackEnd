package org.generation.alicia.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.generation.alicia.model.*;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	public List<Postagem> findAllByUsuario(Usuario usuario);
	
	public List<Postagem> findAllByTextoContainingIgnoreCase(String texto);
	
	public List<Postagem> findAllByTema(Tema tema);

	@Query(value = "select count(tema_id) from tb_postagem where tema_id = :id", nativeQuery = true)
	public long countPosts(@Param("id") long id);
	
}
