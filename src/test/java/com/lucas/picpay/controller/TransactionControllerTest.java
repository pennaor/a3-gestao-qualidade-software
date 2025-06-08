package com.lucas.picpay.controller;

import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.Exception.GerenciamentoDeExceptions;
import com.lucas.picpay.Exception.RegraDeNegocioInvalidaException;
import com.lucas.picpay.Exception.TransacaoNaoAutorizadaException;
import com.lucas.picpay.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService serviceTransacao;

    @InjectMocks
    private TransactionController controller;

    private DtoTransaction mockTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTransaction = new DtoTransaction();
        mockTransaction.setId(1L);
        mockTransaction.setUsuarioTransferenciaId(100L);
        mockTransaction.setUsuarioRecebedorId(200L);
        mockTransaction.setDinheiro(new BigDecimal("50.00"));
    }

    @Test
    void testCriarTransacaoComSucesso() {
        when(serviceTransacao.transferirDinheiro(mockTransaction))
            .thenReturn(mockTransaction);

        ResponseEntity<?> response = controller.criarTransacao(mockTransaction);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockTransaction, response.getBody());
        verify(serviceTransacao, times(1)).transferirDinheiro(mockTransaction);
    }

    @Test
    void testCriarTransacao_TransacaoNaoAutorizada() {
        
        RegraDeNegocioInvalidaException exception = new RegraDeNegocioInvalidaException(
            "O processamento da requisição não foi bem-sucedido por não cumprir as exigências estabelecidas pela aplicação."
        );
        GerenciamentoDeExceptions handler = new GerenciamentoDeExceptions();
        ResponseEntity<DtoException> response = handler.handleRegraDeNegocioInvalida(exception);
        
        DtoException body = response.getBody();
 
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(body);
        assertEquals("O processamento da requisição não foi bem-sucedido por não cumprir as exigências estabelecidas pela aplicação.", body.getMensagem());
        assertEquals("RegraDeNegocioInvalidaException", body.getTipoDeErro());
    }
}