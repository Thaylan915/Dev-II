package com.devWebII.entrega_1.service;

import com.devWebII.entrega_1.domain.Ator;
import com.devWebII.entrega_1.exception.BusinessException;
import com.devWebII.entrega_1.repository.AtorRepository;
import com.devWebII.entrega_1.repository.TituloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;


import java.util.List;

@Service @RequiredArgsConstructor
public class AtorService {
    private final AtorRepository atorRepository;
    private final TituloRepository tituloRepository;

    public List<Ator> findAll() { return atorRepository.findAll(); }
    public Ator findById(Long id) { return atorRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Título não encontrado para o ID " + id)); }
    public Ator save(Ator a) { return atorRepository.save(a); }

    public void deleteById(Long id) {
        if (tituloRepository.existsByAtoresId(id)) {
            throw new BusinessException("Não é possível excluir o ator: há Títulos relacionados.");
        }
        atorRepository.deleteById(id);
    }
}
