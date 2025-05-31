package com.lucas.picpay;

import com.lucas.picpay.controller.UserController;
import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.UsuarioDto;
import com.lucas.picpay.exception.RecursoNaoEncontradoException;
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
        Long id = 1L;
        when(usuarioService.retornarUsuario(id)).thenThrow(new RecursoNaoEncontradoException("Usuário não encontrado"));

        ResponseEntity<?> response = userController.retonarUsuario(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        DtoException dto = (DtoException) response.getBody();
        assertEquals("Usuário não encontrado", dto.getMensagem());
        assertEquals("RecursoNaoEncontradoException", dto.getTipoDeErro());
    }
}
