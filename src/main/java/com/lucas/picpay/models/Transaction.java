package com.lucas.picpay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.dto.DtoTransaction;
import java.math.BigDecimal;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;

@Entity
@Table(name = "tb_transactions")
@Setter
@Getter
public class Transaction
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private long id;
	private long id_payer;
	private long id_payee;
	private BigDecimal dinheiro;
	@ManyToOne
	@JoinColumn( name = "userId_fk")
	private Usuario user;
	
	public Transaction()
	{
		
	}
	
	public Transaction (DtoTransaction transfer)
	{
		this.id_payer = transfer.getPayer_id();
		this.id_payee = transfer.getPayee_id();
		this.dinheiro = transfer.getDinheiro();
	}
}