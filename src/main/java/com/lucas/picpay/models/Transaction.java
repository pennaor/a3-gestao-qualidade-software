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

@Entity @Table(name = "tb_transactions")
@Setter @Getter
public class Transaction
{
@Id
@GeneratedValue( strategy = GenerationType.IDENTITY)
private long id;

private long p_id;

private long p_id2;

private BigDecimal di;

@ManyToOne
@JoinColumn( name = "userId_fk")
private Usuario u;

public Transaction()
{
}

public Transaction (DtoTransaction t)
{
this.id = t.getId();
this.p_id = t.getP_id1();
this.p_id2 = t.getP_id2();
}
}