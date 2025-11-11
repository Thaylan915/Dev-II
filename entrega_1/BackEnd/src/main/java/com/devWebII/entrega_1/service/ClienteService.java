package com.devWebII.entrega_1.service;

import com.devWebII.entrega_1.domain.Cliente;
import com.devWebII.entrega_1.domain.enums.TipoCliente;
import com.devWebII.entrega_1.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository repo;

    public List<Cliente> findAll() { return repo.findAll(); }
    public  Cliente findById(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado para o ID " + id)); }
    public Cliente save(Cliente c) { return repo.save(c); }
    public void deleteById(Long id) { repo.deleteById(id); }

    public List<Cliente> findSocios() {
        return repo.findAll().stream().filter(c -> c.getTipo() == TipoCliente.SOCIO).toList();
    }
}
