package com.lucas.picpay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.dto.UsuarioDto;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.Exception.NotFoundException;


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
		
		Usuario resp = usrRepo.findById(id).orElseThrow(() -> new NotFoundException("Usuario não encontrado!!!"));
		
		return new UsuarioDto(resp);
	}
}
