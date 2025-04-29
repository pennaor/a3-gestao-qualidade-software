package com.lucas.picpay.service;


import com.lucas.picpay.repository.TransactionRepository;
import com.lucas.picpay.repository.UsuarioRepository;
import com.lucas.picpay.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.models.Usuario;
import com.lucas.picpay.Exception.NotFoundException;
import com.lucas.picpay.service.UsuarioService;
import com.lucas.picpay.models.UserType;
import com.lucas.picpay.Exception.BreakRuleSystem;
import com.lucas.picpay.Exception.NotFoundException;
import com.lucas.picpay.Exception.NotAuhtrorizationException;

import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class TransactionService
{
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private TransactionRepository transfeRepo;
	
	
	public DtoTransaction CreaterTransaction(DtoTransaction transferobj)
	{
	
		Usuario objpayer = userRepo.findById(transferobj.getPayer_id()).orElseThrow(() -> new NotFoundException("Usuario não encontrado"));
		Usuario objpayee = userRepo.findById(transferobj.getPayee_id()).orElseThrow(() -> new NotFoundException("Usuario não encontrado"));
		
		
		
		if (objpayee.getUsrtype() == UserType.UserLojista)
		{
			throw new BreakRuleSystem("Um usuario Lojista não pode receber dinheiro");
		}
		
		else
		{
				if (objpayer.getDinheiro().compareTo(transferobj.getDinheiro()) < 0)
				{
					throw new BreakRuleSystem("O saldo do usuario é insuficiente para continuar a transação");
				}
				
				else if (!(validacaoTransaction()))
				{
					throw new NotAuhtrorizationException("Transação recusada pelo sistema");
				}
				
			objpayer.setDinheiro(objpayer.getDinheiro().subtract(transferobj.getDinheiro()));
			objpayee.setDinheiro(objpayee.getDinheiro().add(transferobj.getDinheiro()));

			
			userRepo.save(objpayee);
			userRepo.save(objpayee);
			
		}
		
		return  new DtoTransaction(transfeRepo.save( new Transaction(transferobj)));
	}
	
	public Boolean validacaoTransaction()
	{
		
		
		try
		{
		ResponseEntity<Map> response = new RestTemplate().getForEntity("https://util.devi.tools/api/v2/authorize",Map.class);
		
		if (!(response.getStatusCode() == HttpStatus.OK))
		{
			return false;
		}
		
		Map<String,Object> authorization = (Map<String,Object>) response.getBody().get("data");
		
		return (Boolean) authorization.get("authorization");
		
		}
		
		catch(Exception e)
		{
			return false;
		}
		
		
	}
}
