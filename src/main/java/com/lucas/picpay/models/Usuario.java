package com.lucas.picpay.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import com.lucas.picpay.dto.UsuarioDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.OneToMany;

@Setter
@Getter
@Entity
@Table(name = "tb_usuario")
public class Usuario
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length=30,nullable=false)
	private String nome;
	@Column(columnDefinition = "CHAR(11)",nullable=false,unique=true)
	private String cpf;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable=false)
	private String senha;
	private UserType usrtype;
	private BigDecimal dinheiro;
	@OneToMany(mappedBy = "usuario")
	private List<Transaction> transactions;
	
	
	public Usuario()
	{
		
	}
	
	public Usuario(Long id,String nome,String cpf, String email, String senha, BigDecimal dinheiro)
	{
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.dinheiro = dinheiro;
	}
	
	public Usuario(UsuarioDto usuario)
	{
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
		this.usrtype = usuario.getUsrtype();
		this.dinheiro = usuario.getDinheiro();
	}

}
