package com.devWebII.entrega_1.repository;

import com.devWebII.entrega_1.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> { }
