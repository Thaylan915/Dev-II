package com.devWebII.entrega_1.service;

import com.devWebII.entrega_1.domain.Classe;
import com.devWebII.entrega_1.exception.BusinessException;
import com.devWebII.entrega_1.repository.ClasseRepository;
import com.devWebII.entrega_1.repository.TituloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;


import java.util.List;

@Service @RequiredArgsConstructor
public class ClasseService {
    private final ClasseRepository repo;
    private final TituloRepository tituloRepo;

    public List<Classe> findAll() { return repo.findAll(); }
    public Classe findById(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Título não encontrado para o ID " + id)); }
    public Classe save(Classe c) { return repo.save(c); }
    public void deleteById(Long id) {
        if (tituloRepo.existsByClasseId(id)) {
            throw new BusinessException("Não é possível excluir a classe: há Títulos relacionados.");
        }
        repo.deleteById(id);
    }
}
