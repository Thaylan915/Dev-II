package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.domain.Classe;
import com.devWebII.entrega_1.service.ClasseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


import java.net.URI;
import java.util.List;

@Tag(name = "Classes", description = "Operações de classes")
@RestController @RequiredArgsConstructor
@RequestMapping("/api/classes")
@CrossOrigin
public class ClasseController {
    private final ClasseService service;

    @Operation(summary = "Lista todas as classes")
    @GetMapping public List<Classe> all() { return service.findAll(); }

    @Operation(summary = "Busca classe por ID")
    @ApiResponse(responseCode = "404", description = "Classe não encontrada")
    @GetMapping("/{id}") public Classe one(@PathVariable Long id) { return service.findById(id); }

    @Operation(summary = "Cria classe")
    @PostMapping public ResponseEntity<Classe> create(@RequestBody Classe c) {
        Classe saved = service.save(c);
        return ResponseEntity.created(URI.create("/api/classes/" + saved.getId())).body(saved);
    }

    @Operation(summary = "Atualiza classe por ID")
    @PutMapping("/{id}") public ResponseEntity<Classe> update(@PathVariable Long id, @RequestBody Classe c) {
        c.setId(id); return ResponseEntity.ok(service.save(c));
    }

    @Operation(summary = "Deleta classe por ID")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
