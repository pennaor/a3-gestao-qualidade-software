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
import com.lucas.picpay.Exception.Nfound;
import com.lucas.picpay.dto.DtoException;



@RestController
@RequestMapping(value = "/api")
public class UserController {
	
	@Autowired
	private UsuarioService usrService;
	
	@PostMapping
	public ResponseEntity<UsuarioDto> saveUser(@RequestBody UsuarioDto userdto)
	{
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usrService.saveUser(userdto));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUser(@PathVariable long id)
	{
		try {
		return ResponseEntity.status(HttpStatus.OK).body(usrService.getUser(id));
		}
		
		catch (Nfound e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
		}
	}
	
	@Service
	public class UsuarioService
	{
		
		@Autowired
		private UsuarioRepository usrRepo;
		
		public UsuarioDto saveUser(UsuarioDto usr)
		{
			Usuario usrmodel = new Usuario(usr);
			
			Usuario resp = usrRepo.save(usrmodel);
			
			return new UsuarioDto(resp);
			
		}
		
		public UsuarioDto getUser(Long id)
		{
			
			Usuario resp = usrRepo.findById(id).orElseThrow(() -> new Nfound("Usuario não encontrado!!!"));
			
			return new UsuarioDto(resp);
		}
	}
	
}
