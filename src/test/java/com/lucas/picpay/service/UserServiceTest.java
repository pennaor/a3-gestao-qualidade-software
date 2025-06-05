package com.lucas.picpay.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.lucas.picpay.Exception.RecursoNaoEncontradoException;
import com.lucas.picpay.dto.UsuarioDto;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class UserServiceTest {

    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarUsuario_Sucesso() {
        UsuarioDto dto = new UsuarioDto();
        // Configure dto com os dados necessários se precisar

        Usuario usuarioSalvo = new Usuario(dto);
        usuarioSalvo.setId(1L); // Simula id criado

        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioDto resultado = userService.CriarUsuario(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(usuarioRepo, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testRetornarUsuario_Sucesso() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Lucas");

        when(usuarioRepo.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioDto resultado = userService.retornarUsuario(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Lucas", resultado.getNome());
        verify(usuarioRepo, times(1)).findById(id);
    }

    @Test
    public void testRetornarUsuario_NaoEncontrado() {
        Long id = 2L;

        when(usuarioRepo.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class,
                () -> userService.retornarUsuario(id));

        assertEquals("Usuario não encontrado no sistema!!!", ex.getMessage());
        verify(usuarioRepo, times(1)).findById(id);
    }
}
