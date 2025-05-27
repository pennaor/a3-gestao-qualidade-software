package com.lucas.picpay.dto;

import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.models.*;
import java.math.BigDecimal;


@Getter
@Setter
public class UsuarioDto
{
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private UserType usrtype;
	private BigDecimal dinheiro;
	
	public UsuarioDto (Usuario usuario)
	{
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.usrtype = usuario.getUsrtype();
		this.dinheiro = usuario.getDinheiro();
		
		
	}
	
	public UsuarioDto()
	{
		
	}
	
}
