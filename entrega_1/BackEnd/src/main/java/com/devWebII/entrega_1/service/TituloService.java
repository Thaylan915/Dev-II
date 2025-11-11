package com.devWebII.entrega_1.service;

import com.devWebII.entrega_1.domain.Titulo;
import com.devWebII.entrega_1.repository.TituloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;


import java.util.List;

@Service @RequiredArgsConstructor
public class TituloService {
    private final TituloRepository repo;
    public List<Titulo> findAll() { return repo.findAll(); }
    public Titulo findById(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Título não encontrado para o ID " + id)); }
    public Titulo save(Titulo t) { return repo.save(t); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
