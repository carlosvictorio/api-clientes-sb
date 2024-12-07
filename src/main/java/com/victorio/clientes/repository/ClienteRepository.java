package com.victorio.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorio.clientes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
