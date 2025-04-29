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
	
	public UsuarioDto (Usuario usr)
	{
		this.id = usr.getId();
		this.nome = usr.getNome();
		this.cpf = usr.getCpf();
		this.email = usr.getEmail();
		this.senha = usr.getSenha();
		this.usrtype = usr.getUsrtype();
		this.dinheiro = usr.getDinheiro();
		
		
	}
	
	public UsuarioDto()
	{
		
	}
	
}
