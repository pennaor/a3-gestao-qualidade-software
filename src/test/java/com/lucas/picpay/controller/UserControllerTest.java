package com.lucas.picpay.controller;

import com.lucas.picpay.controller.UserController;
import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.UsuarioDto;
import com.lucas.picpay.Exception.GerenciamentoDeExceptions;
import com.lucas.picpay.Exception.RecursoNaoEncontradoException;
import com.lucas.picpay.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService usuarioService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarUsuarioSuccess() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNome("Lucas");

        when(usuarioService.CriarUsuario(usuarioDto)).thenReturn(usuarioDto);

        ResponseEntity<?> response = userController.CriarUsuarior(usuarioDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuarioDto, response.getBody());
    }

    @Test
    void testRetornarUsuarioSuccess() {
        Long id = 1L;
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(id);

        when(usuarioService.retornarUsuario(id)).thenReturn(usuarioDto);

        ResponseEntity<?> response = userController.retonarUsuario(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioDto, response.getBody());
    }

    @Test
    void testRetornarUsuarioNotFound() {
    	
    	RecursoNaoEncontradoException objetoException = new RecursoNaoEncontradoException("O recurso solicitado não foi encontrado!");
    	GerenciamentoDeExceptions objetoGerenciarExceptions = new GerenciamentoDeExceptions();
    	ResponseEntity<DtoException> response = objetoGerenciarExceptions.handleRecursoNaoEncontrado(objetoException);
    	
    	
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("O recurso solicitado não foi encontrado!", response.getBody().getMensagem());
        assertEquals("RecursoNaoEncontradoException", response.getBody().getTipoDeErro());
    }
}
