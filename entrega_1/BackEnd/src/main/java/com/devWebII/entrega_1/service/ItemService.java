package com.devWebII.entrega_1.service;

import com.devWebII.entrega_1.domain.Item;
import com.devWebII.entrega_1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;


import java.util.List;

@Service @RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repo;
    public List<Item> findAll() { return repo.findAll(); }
    public Item findById(Long id) { return repo.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrado para o ID " + id)); }
    public Item save(Item i) { return repo.save(i); }
    public void deleteById(Long id) { repo.deleteById(id); }
}
