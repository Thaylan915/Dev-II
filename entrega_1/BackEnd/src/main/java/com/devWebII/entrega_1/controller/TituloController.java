package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.domain.Titulo;
import com.devWebII.entrega_1.service.TituloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


import java.net.URI;
import java.util.List;

@Tag(name = "Títulos", description = "Operações de títulos")
@RestController @RequiredArgsConstructor
@RequestMapping("/api/titulos")
@CrossOrigin
public class TituloController {
    private final TituloService service;

    @Operation(summary = "Lista todos os títulos")
    @GetMapping public List<Titulo> all() { return service.findAll(); }
    @GetMapping("/{id}") public Titulo one(@PathVariable Long id) { return service.findById(id); }

    @Operation(summary = "Cria título")
    @PostMapping public ResponseEntity<Titulo> create(@RequestBody Titulo t) {
        Titulo saved = service.save(t);
        return ResponseEntity.created(URI.create("/api/titulos/" + saved.getId())).body(saved);
    }

    @Operation(summary = "Atualiza título por ID")
    @PutMapping("/{id}") public ResponseEntity<Titulo> update(@PathVariable Long id, @RequestBody Titulo t) {
        t.setId(id); return ResponseEntity.ok(service.save(t));
    }

    @Operation(summary = "Deleta título por ID")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
