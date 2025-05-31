package com.lucas.picpay.controller;

import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.exception.RecursoNaoEncontradoException;
import com.lucas.picpay.exception.RegraDeNegocioInvalidaException;
import com.lucas.picpay.exception.TransacaoNaoAutorizadaException;
import com.lucas.picpay.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        mockTransaction.setUsuarioTransferencia_id(100L);
        mockTransaction.setUsuarioRecebedor_id(200L);
        mockTransaction.setDinheiro(new BigDecimal("50.00"));
    }

    @Test
    void testCriarTransacaoComSucesso() {
        when(serviceTransacao.transferirDinheiro(mockTransaction)).thenReturn(mockTransaction);

        ResponseEntity<?> response = controller.criarTransacao(mockTransaction);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockTransaction, response.getBody());
        verify(serviceTransacao, times(1)).transferirDinheiro(mockTransaction);
    }

    @Test
    void testCriarTransacao_RecursoNaoEncontrado() {
        when(serviceTransacao.transferirDinheiro(mockTransaction))
            .thenThrow(new RecursoNaoEncontradoException("Usuário não encontrado"));

        ResponseEntity<?> response = controller.criarTransacao(mockTransaction);

        assertEquals(404, response.getStatusCodeValue());
        DtoException body = (DtoException) response.getBody();
        assertEquals("Usuário não encontrado", body.getMensagem());
        assertEquals("RecursoNaoEncontradoException", body.getTipoDeErro());
    }

    @Test
    void testCriarTransacao_RegraDeNegocioInvalida() {
        when(serviceTransacao.transferirDinheiro(mockTransaction))
            .thenThrow(new RegraDeNegocioInvalidaException("Saldo insuficiente"));

        ResponseEntity<?> response = controller.criarTransacao(mockTransaction);

        assertEquals(400, response.getStatusCodeValue());
        DtoException body = (DtoException) response.getBody();
        assertEquals("Saldo insuficiente", body.getMensagem());
        assertEquals("RegraDeNegocioInvalidaException", body.getTipoDeErro());
    }

    @Test
    void testCriarTransacao_TransacaoNaoAutorizada() {
        when(serviceTransacao.transferirDinheiro(mockTransaction))
            .thenThrow(new TransacaoNaoAutorizadaException("Transação não autorizada"));

        ResponseEntity<?> response = controller.criarTransacao(mockTransaction);

        assertEquals(405, response.getStatusCodeValue());
        DtoException body = (DtoException) response.getBody();
        assertEquals("Transação não autorizada", body.getMensagem());
        assertEquals("TransacaoNaoAutorizadaException", body.getTipoDeErro());
    }
}
