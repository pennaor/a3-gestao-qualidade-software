package com.lucas.picpay.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.lucas.picpay.dto.DtoException;

@ControllerAdvice
public class GerenciamentoDeExceptions {
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<DtoException> handleRecursoNaoEncontrado(RecursoNaoEncontradoException e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DtoException("O recurso solicitado não foi encontrado!!!",e.getClass().getSimpleName()));
	}
	
	@ExceptionHandler(RegraDeNegocioInvalidaException.class)
	public ResponseEntity<DtoException> handleRegraDeNegocioInvalida(RegraDeNegocioInvalidaException e)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DtoException("O processamento da requisição não foi bem sucedida por não cumprir as exigencias estabelecidas da aplicação",e.getClass().getSimpleName()));
	}
	
	@ExceptionHandler(TransacaoNaoAutorizadaException.class)
	public ResponseEntity<DtoException> handleTrasacaoNaoAutorizada(TransacaoNaoAutorizadaException e)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DtoException("A transação não cumpriu todos os requisitos estabelecidos no sistema",e.getClass().getSimpleName()));
	}

}
