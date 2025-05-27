package com.lucas.picpay.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DtoException {
	
	private String mensagem;
	private String tipoDeErro;
	
	public DtoException()
	{
		
	}
	
	public DtoException(String mensagem, String tipoDeErro)
	{
		this.mensagem = mensagem;
		this.tipoDeErro = tipoDeErro;
	}
	
}
