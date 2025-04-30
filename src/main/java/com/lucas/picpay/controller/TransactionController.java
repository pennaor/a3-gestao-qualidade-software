package com.lucas.picpay.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.lucas.picpay.Exception.Nfound;
import com.lucas.picpay.Exception.Not;
import com.lucas.picpay.Exception.Break;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionController {
	
	
	@Autowired
	private TransactionService transferService;
	
	@PostMapping
	public ResponseEntity<?> getIdTransaction(@RequestBody DtoTransaction transfer)
	{
		
		try
		{
		return ResponseEntity.status(HttpStatus.CREATED).body(transferService.CreaterTransaction(transfer));
		}
		
		catch(Nfound e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
}

catch (Break e)
{
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
}

catch (Not e)
{
	return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
	}
}


@Service
public class TransactionService
{
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired
	private TransactionRepository transfeRepo;
	
	
	public DtoTransaction CreaterTransaction(DtoTransaction transferobj)
	{
	
		Usuario objp1 = userRepo.findById(transferobj.getP_id1()).orElseThrow(() -> new Nfound("Usuario não encontrado"));
Usuario objp2 = userRepo.findById(transferobj.getP_id2()).orElseThrow(() -> new Nfound("Usuario não encontrado"));



if (objp1.getUsrtype() == UserType.UL)
{
	throw new Break("...");
}

else
{
		if (objp2.getDinheiro().compareTo(transferobj.getDi()) < 0)
		{
			throw new Break(".");
}

else if (!valT())
{
	throw new Not("...");
			}
			
		objp1.setDinheiro(objp1.getDinheiro().subtract(transferobj.getDi()));
		objp2.setDinheiro(objp2.getDinheiro().add(transferobj.getDi()));

		
		userRepo.save(objp1);
		userRepo.save(objp2);
		
	}
	
	return  new DtoTransaction(transfeRepo.save( new Transaction(transferobj)));
}

public Boolean valT()
{
	
	
	try
	{
	ResponseEntity<Map> r = new RestTemplate().getForEntity("https://util.devi.tools/api/v2/authorize",Map.class);

if (!(r.getStatusCode() == HttpStatus.OK))
{
	return false;
}

Map<String,Object> a = (Map<String,Object>) r.getBody().get("data");

return (Boolean) a.get("authorization");
	
			}
			
			catch(Exception e)
			{
				return false;
			}
			
			
		}
	}
	
}
