package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.domain.Ator;
import com.devWebII.entrega_1.service.AtorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


import java.net.URI;
import java.util.List;

@Tag(name = "Atores", description = "Operações de atores")
@RestController @RequiredArgsConstructor
@RequestMapping("/api/atores")
@CrossOrigin
public class AtorController {
    private final AtorService service;

    @Operation(summary = "Lista todos os atores")
    @GetMapping public List<Ator> all() { return service.findAll(); }

    @Operation(summary = "Busca ator por ID")
    @ApiResponse(responseCode = "404", description = "Ator não encontrado")
    @GetMapping("/{id}") public Ator one(@PathVariable Long id) { return service.findById(id); }

    @Operation(summary = "Cria ator")
    @PostMapping public ResponseEntity<Ator> create(@RequestBody Ator ator) {
        Ator saved = service.save(ator);
        return ResponseEntity.created(URI.create("/api/atores/" + saved.getId())).body(saved);
    }
    @Operation(summary = "Atualiza ator por ID")
    @PutMapping("/{id}") public ResponseEntity<Ator> update(@PathVariable Long id, @RequestBody Ator ator) {
        ator.setId(id); return ResponseEntity.ok(service.save(ator));
    }

    @Operation(summary = "Deleta ator por ID")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
