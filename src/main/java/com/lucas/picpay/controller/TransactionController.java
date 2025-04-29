package com.lucas.picpay.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucas.picpay.dto.DtoException;
import com.lucas.picpay.dto.DtoTransaction;
import com.lucas.picpay.service.TransactionService;
import com.lucas.picpay.Exception.NotFoundException;
import com.lucas.picpay.Exception.NotAuhtrorizationException;
import com.lucas.picpay.Exception.BreakRuleSystem;

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
		
		catch(NotFoundException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
		}
		
		catch (BreakRuleSystem e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
		}
		
		catch (NotAuhtrorizationException e)
		{
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new DtoException(e.getMessage(),e.getClass().getName().split("\\.")[4]));
		}
	}
	
}
