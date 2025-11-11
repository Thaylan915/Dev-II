package com.devWebII.entrega_1.service;

import com.devWebII.entrega_1.domain.Diretor;
import com.devWebII.entrega_1.exception.BusinessException;
import com.devWebII.entrega_1.repository.DiretorRepository;
import com.devWebII.entrega_1.repository.TituloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;


import java.util.List;

@Service @RequiredArgsConstructor
public class DiretorService {
    private final DiretorRepository repo;
    private final TituloRepository tituloRepo;

    public List<Diretor> findAll() { return repo.findAll(); }
    public Diretor findById(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Título não encontrado para o ID " + id)); }
    public Diretor save(Diretor d) { return repo.save(d); }
    public void deleteById(Long id) {
        if (tituloRepo.existsByDiretoresId(id)) {
            throw new BusinessException("Não é possível excluir o diretor: há Títulos relacionados.");
        }
        repo.deleteById(id);
    }
}
