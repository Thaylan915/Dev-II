package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.domain.Item;
import com.devWebII.entrega_1.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


import java.net.URI;
import java.util.List;

@Tag(name = "Itens", description = "Operações de itens")
@RestController @RequiredArgsConstructor
@RequestMapping("/api/itens")
@CrossOrigin
public class ItemController {
    private final ItemService service;

    @Operation(summary = "Lista todos os itens")
    @GetMapping public List<Item> all() { return service.findAll(); }
    @GetMapping("/{id}") public Item one(@PathVariable Long id) { return service.findById(id); }

    @Operation(summary = "Cria item")
    @PostMapping public ResponseEntity<Item> create(@RequestBody Item i) {
        Item saved = service.save(i);
        return ResponseEntity.created(URI.create("/api/itens/" + saved.getId())).body(saved);
    }

    @Operation(summary = "Atualiza item por ID")
    @PutMapping("/{id}") public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item i) {
        i.setId(id); return ResponseEntity.ok(service.save(i));
    }

    @Operation(summary = "Deleta item por ID")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
