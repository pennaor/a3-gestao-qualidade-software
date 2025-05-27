package com.lucas.picpay.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.models.Transaction;


@Getter
@Setter
public class DtoTransaction {
	
	private long id;
	private long usuarioTransferencia_id;
	private long usuarioRecebedor_id;
	private BigDecimal dinheiro;
	
	public DtoTransaction()
	{
		
	}
	
	public DtoTransaction(Transaction transferencia) 
	{
		this.id = transferencia.getId();
		this.usuarioTransferencia_id = transferencia.getUsuarioTransferencia_id();
		this.usuarioRecebedor_id = transferencia.getUsuarioReceber_id();
		this.dinheiro = transferencia.getDinheiro();
	}
}
