package com.lucas.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lucas.picpay.models.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
}
