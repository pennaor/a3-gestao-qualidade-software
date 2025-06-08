package com.lucas.picpay.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import com.lucas.picpay.Exception.RegraDeNegocioInvalidaException;
import com.lucas.picpay.Exception.RecursoNaoEncontradoException;
import com.lucas.picpay.Exception.TransacaoNaoAutorizadaException;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.models.Transaction;
import com.lucas.picpay.models.UserType;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.repository.TransactionRepository;
import com.lucas.picpay.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private AuthTransacaoService authTransacaoService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    public void testTransferirDinheiro_Sucesso() {
       
        Usuario usuarioTransferir = new Usuario();
        usuarioTransferir.setId(1L);
        usuarioTransferir.setUsrtype(UserType.USUARIO_COMUM);
        usuarioTransferir.setDinheiro(new BigDecimal("100.00"));

        Usuario usuarioReceber = new Usuario();
        usuarioReceber.setId(2L);
        usuarioReceber.setDinheiro(new BigDecimal("50.00"));

      
        DtoTransaction dto = new DtoTransaction();
        dto.setUsuarioTransferenciaId(1L);
        dto.setUsuarioRecebedorId(2L);
        dto.setDinheiro(new BigDecimal("30.00"));

        
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuarioTransferir));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.of(usuarioReceber));
        when(authTransacaoService.validacaoTransicao()).thenReturn(true);

       
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction t = invocation.getArgument(0);
            t.setId(10L);
            return t;
        });

        DtoTransaction result = transactionService.transferirDinheiro(dto);

        
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(new BigDecimal("70.00"), usuarioTransferir.getDinheiro()); // 100 - 30
        assertEquals(new BigDecimal("80.00"), usuarioReceber.getDinheiro());    // 50 + 30

      
        verify(usuarioRepo, times(1)).save(usuarioTransferir);
        verify(usuarioRepo, times(1)).save(usuarioReceber);
        verify(transactionRepo, times(1)).save(any(Transaction.class));
    }

    
    @Test
    public void testTransferirDinheiro_UsuarioTransferirNaoEncontrado() {
        DtoTransaction dto = new DtoTransaction();
        dto.setUsuarioTransferenciaId(1L);

        when(usuarioRepo.findById(1L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class,
            () -> transactionService.transferirDinheiro(dto));

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

   
    @Test
    public void testTransferirDinheiro_UsuarioReceptorNaoEncontrado() {
        DtoTransaction dto = new DtoTransaction();
        dto.setUsuarioTransferenciaId(1L);
        dto.setUsuarioRecebedorId(2L);

        Usuario usuarioTransferir = new Usuario();
        usuarioTransferir.setId(1L);
        usuarioTransferir.setUsrtype(UserType.USUARIO_COMUM);
        usuarioTransferir.setDinheiro(new BigDecimal("100.00"));

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuarioTransferir));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class,
            () -> transactionService.transferirDinheiro(dto));

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    // Usuário lojista tenta transferir
    @Test
    public void testTransferirDinheiro_UsuarioLojista() {
        DtoTransaction dto = new DtoTransaction();
        dto.setUsuarioTransferenciaId(1L);
        dto.setUsuarioRecebedorId(2L);
        dto.setDinheiro(new BigDecimal("10.00"));

        Usuario usuarioTransferir = new Usuario();
        usuarioTransferir.setId(1L);
        usuarioTransferir.setUsrtype(UserType.USUARIO_LOJISTA);
        usuarioTransferir.setDinheiro(new BigDecimal("100.00"));

        Usuario usuarioReceber = new Usuario();
        usuarioReceber.setId(2L);
        usuarioReceber.setDinheiro(new BigDecimal("50.00"));

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuarioTransferir));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.of(usuarioReceber));

        RegraDeNegocioInvalidaException ex = assertThrows(RegraDeNegocioInvalidaException.class,
            () -> transactionService.transferirDinheiro(dto));

        assertEquals("Usuário do tipo lojista não pode transferir dinheiro", ex.getMessage());
    }

    // Saldo insuficiente
    @Test
    public void testTransferirDinheiro_SaldoInsuficiente() {
        DtoTransaction dto = new DtoTransaction();
        dto.setUsuarioTransferenciaId(1L);
        dto.setUsuarioRecebedorId(2L);
        dto.setDinheiro(new BigDecimal("150.00")); // maior que saldo do usuário

        Usuario usuarioTransferir = new Usuario();
        usuarioTransferir.setId(1L);
        usuarioTransferir.setUsrtype(UserType.USUARIO_COMUM);
        usuarioTransferir.setDinheiro(new BigDecimal("100.00"));

        Usuario usuarioReceber = new Usuario();
        usuarioReceber.setId(2L);
        usuarioReceber.setDinheiro(new BigDecimal("50.00"));

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuarioTransferir));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.of(usuarioReceber));

        RegraDeNegocioInvalidaException ex = assertThrows(RegraDeNegocioInvalidaException.class,
            () -> transactionService.transferirDinheiro(dto));

        assertEquals("Saldo insuficiente", ex.getMessage());
    }

    // Transação não autorizada pela validação
    @Test
    public void testTransferirDinheiro_TransacaoNaoAutorizada() {
        DtoTransaction dto = new DtoTransaction();
        dto.setUsuarioTransferenciaId(1L);
        dto.setUsuarioRecebedorId(2L);
        dto.setDinheiro(new BigDecimal("50.00"));

        Usuario usuarioTransferir = new Usuario();
        usuarioTransferir.setId(1L);
        usuarioTransferir.setUsrtype(UserType.USUARIO_COMUM);
        usuarioTransferir.setDinheiro(new BigDecimal("100.00"));

        Usuario usuarioReceber = new Usuario();
        usuarioReceber.setId(2L);
        usuarioReceber.setDinheiro(new BigDecimal("50.00"));

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuarioTransferir));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.of(usuarioReceber));
        when(authTransacaoService.validacaoTransicao()).thenReturn(false);

        TransacaoNaoAutorizadaException ex = assertThrows(TransacaoNaoAutorizadaException.class,
            () -> transactionService.transferirDinheiro(dto));

        assertEquals("Transferência não autorizada", ex.getMessage());
    }
}
