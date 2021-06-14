package org.generation.alicia.controller;

import java.util.List;
import java.util.Optional;

import org.generation.alicia.model.Usuario;
import org.generation.alicia.model.UsuarioLogin;
import org.generation.alicia.repository.UsuarioRepository;
import org.generation.alicia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuario")
public class UsuarioController {
	
	//Injeção de Dependências
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository repository;
	
	// Métodos Get
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable long id){
			return repository.findById(id)
					.map(resp -> ResponseEntity.ok(resp))
					.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("nome/{nome}")
	public ResponseEntity<List<Usuario>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/email/{login}")
	public ResponseEntity<Usuario> getByLogin(@PathVariable String login) {
		return repository.findByLogin(login)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}/login/{login}")
	public ResponseEntity<Usuario> getByNomeLogin(@PathVariable String nome, @PathVariable String login){
		
		try {
			return ResponseEntity.ok(repository.findByNomeAndLogin(nome, login));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	// Métodos Post
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioLogin> authentication(@RequestBody Optional<UsuarioLogin> user) {
		return usuarioService.Logar(user)
				.map(resp-> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<Optional<Usuario>> cadastro(@RequestBody Usuario usuario) {
		Optional<Usuario> user = usuarioService.CadastrarUsuario(usuario);
		if(user != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// Métodos Put
	
	@PutMapping("/alterar")
	public ResponseEntity<Usuario> alterarSenha(@RequestBody Usuario usuario) {
		Optional<Usuario> user = usuarioService.atualizarUsuario(usuario);
		
		try {
			return ResponseEntity.ok(user.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	/* Função desabilitada para evitar problemas;
	 *@DeleteMapping("/{id}")
	 *public void DeleteById(@PathVariable long id){
	 *repository.deleteById(id);
	}*/

}