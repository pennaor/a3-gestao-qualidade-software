package com.lucas.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

import com.lucas.picpay.models.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>
{
	    @Modifying
	    @Transactional
	    @Query("UPDATE Usuario u SET u.dinheiro = :dinheiro WHERE u.id = :id")
	    public int update(@Param("id") long id, @Param("dinheiro") BigDecimal dinheiro);

	
}
