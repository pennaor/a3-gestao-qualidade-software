package com.lucas.picpay;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.repository.TransactionRepository;
import com.lucas.picpay.service.AuthTransacaoService;
import com.lucas.picpay.service.TransactionService;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTeste {
	
	
	@Mock
	private UsuarioRepository usuarioRepo;
	
	@Mock
	private TransactionRepository transactionRepo;
	
	@Mock
	private AuthTransacaoService authTransacaoService;
	
	@Autowired
	@InjectMocks
	private TransactionService transacaoService;
	
	@Test
	public void transacaoCase1()
	{
		return;
	}
	
	
}
