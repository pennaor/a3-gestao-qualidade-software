package com.lucas.picpay.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.models.Transaction;


@Getter
@Setter
public class DtoTransaction {
	
	private long id;
	private long payer_id;
	private long payee_id;
	private BigDecimal dinheiro;
	
	public DtoTransaction()
	{
		
	}
	
	public DtoTransaction(Transaction transfer) 
	{
		this.id = transfer.getId();
		this.payer_id = transfer.getId_payer();
		this.payee_id = transfer.getId_payee();
		this.dinheiro = transfer.getDinheiro();
	}
}
