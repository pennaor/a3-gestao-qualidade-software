package com.lucas.picpay.Exception;



public class TransacaoNaoAutorizadaException extends RuntimeException
{
	public TransacaoNaoAutorizadaException(String msg)
	{
		super(msg);
	}
}
