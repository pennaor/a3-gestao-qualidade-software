package com.lucas.picpay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

import com.lucas.picpay.dto.DtoTransaction;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_transactions")
@Setter
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long usuarioTransferenciaId;

    private long usuarioReceberId;

    private BigDecimal dinheiro;

    @ManyToOne
    @JoinColumn(name = "userId_fk")
    private Usuario usuario;

    public Transaction() {
    }

    public Transaction(DtoTransaction dtoTransferencia) {
        this.id = dtoTransferencia.getId();
        this.usuarioTransferenciaId = dtoTransferencia.getUsuarioTransferenciaId();
        this.usuarioReceberId = dtoTransferencia.getUsuarioRecebedorId();
        this.dinheiro  = dtoTransferencia.getDinheiro();
    }
}