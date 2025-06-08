package com.lucas.picpay.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.models.Transaction;


@Getter
@Setter
public class DtoTransaction {
	
	private long id;
	private long usuarioTransferenciaId;
	private long usuarioRecebedorId;
	private BigDecimal dinheiro;
	
	public DtoTransaction()
	{
		
	}
	
	public DtoTransaction(Transaction transferencia) 
	{
		this.id = transferencia.getId();
		this.usuarioTransferenciaId = transferencia.getUsuarioTransferenciaId();
		this.usuarioRecebedorId = transferencia.getUsuarioReceberId();
		this.dinheiro = transferencia.getDinheiro();
	}
}
