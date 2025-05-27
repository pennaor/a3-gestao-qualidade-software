package com.lucas.picpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.UsuarioDto;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.Exception.RecursoNaoEncontradoException;

@Service
public class UserService
{
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	public UsuarioDto CriarUsuario(UsuarioDto dtoNovoUsuario)
	{
		Usuario  novoUsuario= new Usuario(dtoNovoUsuario);
		
		Usuario resposta = usuarioRepo.save(novoUsuario);
		
		
		return new UsuarioDto(resposta);
	}
	
	public UsuarioDto retornarUsuario(Long id)
	{
		
		Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado no sistema!!!"));
		
		return new UsuarioDto(usuario);
		
		
	}
}
