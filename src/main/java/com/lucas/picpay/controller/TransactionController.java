package com.lucas.picpay.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.models.Transaction;
import com.lucas.picpay.models.UserType;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.repository.TransactionRepository;
import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.Exception.RecursoNaoEncontradoException;
import com.lucas.picpay.Exception.TransacaoNaoAutorizadaException;
import com.lucas.picpay.Exception.RegraDeNegocioInvalidaException;
import com.lucas.picpay.service.TransactionService;

@RestController
@RequestMapping(value = "/api/v1/transaction")
public class TransactionController
{
	@Autowired
	private TransactionService serviceTransacao;
	@PostMapping
	public ResponseEntity<?> criarTransacao(@RequestBody DtoTransaction dtoTransferencia)
	{
			DtoTransaction dtoTransferenciaResponse = serviceTransacao.transferirDinheiro(dtoTransferencia);
			return ResponseEntity.status(HttpStatus.CREATED ).body(dtoTransferenciaResponse);
		
	
}
}
