package com.lucas.picpay.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.models.Transaction;


@Getter
@Setter
public class DtoTransaction {
	
	private long id;
	private long p_id1;
	private long p_id2;
	private BigDecimal di;
	
	public DtoTransaction()
	{
		
	}
	
	public DtoTransaction(Transaction transfer) 
	{
		this.id = transfer.getId();
		this.p_id2 = transfer.getP_id();
		this.p_id2 = transfer.getP_id2();
		this.di = transfer.getDi();
	}
}
