package com.victorio.clientes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.clientes.model.Cliente;
import com.victorio.clientes.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteRepository.save(cliente);
		//return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> getAll() {
		List<Cliente> clientes = clienteRepository.findAll();
		if(clientes.isEmpty()) {
			//return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.ok().body(clientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getById(@PathVariable Long id) {
		if(!clienteRepository.existsById(id)) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return ResponseEntity.ok().body(cliente.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);
		
		Cliente clienteAtualizado = clienteExistente.get();
		
		clienteAtualizado.setEmail(cliente.getEmail());
		clienteAtualizado.setName(cliente.getName());
		clienteAtualizado.setEndereco(cliente.getEndereco());
		clienteAtualizado.setTelefone(cliente.getTelefone());
		
		clienteRepository.save(clienteAtualizado);
		
		return ResponseEntity.status(HttpStatus.OK).body(clienteAtualizado);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cliente> delete(@PathVariable Long id) {
		if(!clienteRepository.existsById(id)) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Optional<Cliente> clienteApagado = clienteRepository.findById(id);
		
		clienteRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(clienteApagado.get());
		
	}
	
}
