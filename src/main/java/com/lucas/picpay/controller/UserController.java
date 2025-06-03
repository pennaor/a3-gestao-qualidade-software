package com.lucas.picpay.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.lucas.picpay.dto.UsuarioDto;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.Exception.RecursoNaoEncontradoException;
import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.service.UserService;



@RestController
@RequestMapping(value = "/api/v1")
public class UserController {
	
	@Autowired
	private UserService usuarioService;
	
	
	@PostMapping(value ="/user")
	public ResponseEntity<?> CriarUsuarior(@RequestBody UsuarioDto dtoUsuario)
	{
		UsuarioDto response = usuarioService.CriarUsuario(dtoUsuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<?> retonarUsuario (@PathVariable Long id)
	{
			UsuarioDto reposta = usuarioService.retornarUsuario(id);
			
		    return ResponseEntity.status(HttpStatus.OK).body(reposta);
	}
}
