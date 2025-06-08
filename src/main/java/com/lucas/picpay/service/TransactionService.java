package com.lucas.picpay.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lucas.picpay.repository.TransactionRepository;
import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.Exception.RegraDeNegocioInvalidaException;
import com.lucas.picpay.Exception.RecursoNaoEncontradoException;
import com.lucas.picpay.Exception.TransacaoNaoAutorizadaException;
import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.models.Transaction;
import com.lucas.picpay.models.UserType;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.service.AuthTransacaoService;
@Service
public class TransactionService {

    private  TransactionRepository transacaoRepo;
    private  UsuarioRepository usuarioRepo;
    private AuthTransacaoService autorizacaoService;

    public TransactionService(TransactionRepository transacaoRepo, UsuarioRepository usuarioRepo,AuthTransacaoService autorizacaoService)
    {
    	this.autorizacaoService = autorizacaoService;
        this.transacaoRepo = transacaoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public DtoTransaction transferirDinheiro(DtoTransaction dtoTransferencia)
    {
    	
        	Usuario usuarioTransferir = usuarioRepo.findById(dtoTransferencia.getUsuarioTransferenciaId()).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
            Usuario usuarioReceptor = usuarioRepo.findById(dtoTransferencia.getUsuarioRecebedorId()).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
            Transaction transferencia = new Transaction(dtoTransferencia);

            if (usuarioTransferir.getUsrtype() == UserType.USUARIO_LOJISTA)
                throw new RegraDeNegocioInvalidaException("Usuário do tipo lojista não pode transferir dinheiro");

            if (usuarioTransferir.getDinheiro().compareTo(transferencia.getDinheiro()) < 0)
                throw new RegraDeNegocioInvalidaException("Saldo insuficiente");

            if (!autorizacaoService.validacaoTransicao())
                throw new TransacaoNaoAutorizadaException("Transferência não autorizada");

            usuarioTransferir.setDinheiro(usuarioTransferir.getDinheiro().subtract(transferencia.getDinheiro()));
            usuarioReceptor.setDinheiro(usuarioReceptor.getDinheiro().add(transferencia.getDinheiro()));
            
            transferencia.setUsuario(usuarioTransferir);
            usuarioRepo.save(usuarioTransferir);
            usuarioRepo.save(usuarioReceptor);
            Transaction resposta = transacaoRepo.save(transferencia);
           
            return new DtoTransaction(resposta);

        }
    
}
