package com.lucas.picpay.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DtoException {
	
	private String msg;
	private String typeError;
	
	public DtoException()
	{
		
	}
	
	public DtoException(String msg, String typeError)
	{
		this.msg = msg;
		this.typeError = typeError;
	}
	
}
