package org.generation.alicia.service;

import java.nio.charset.Charset;
import java.time.Period;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.alicia.model.Usuario;
import org.generation.alicia.model.UsuarioLogin;
import org.generation.alicia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	//Injecao de Dependencias
	
	@Autowired
	private UsuarioRepository repository;
	
	// Cadastro e Login
	
	public Optional<Usuario> CadastrarUsuario(Usuario login) {
		
		if(repository.findByLogin(login.getLogin()).isPresent()) { // Checa se o login(email) do usuario já esta cadastrado no banco
			return null;
		}
		
		int idade = Period.between(login.getDataNascimento(), LocalDate.now()).getYears();
		
		if(idade < 16) { // Checa se o usuário tem mais de 16 anos
			return null;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder ();
		String senhaEncoder = encoder.encode(login.getSenha());
		login.setSenha(senhaEncoder);
		
		return Optional.of(repository.save(login));
	}
	
	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> user){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> login = repository.findByLogin(user.get().getLogin());
		
		if(login.isPresent()) {
			if(encoder.matches(user.get().getSenha(), login.get().getSenha())) {
				
				String auth = user.get().getLogin()+ ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);
				
				user.get().setToken(authHeader);
				user.get().setId(login.get().getId());
				user.get().setNome(login.get().getNome());
				user.get().setFoto(login.get().getFoto());
				user.get().setTipo(login.get().getTipo());
				
				return user;
			}
		}
		
		return null;
	}
	
	// Métodos para alterar dados do Usuario
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		if(!repository.findByLogin(usuario.getLogin()).isPresent()) {
			return null;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaEncoder = encoder.encode(usuario.getSenha());
		
		usuario.setSenha(senhaEncoder);
		
		return Optional.of(repository.save(usuario));
	}
	
}
