package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.domain.Diretor;
import com.devWebII.entrega_1.service.DiretorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;


import java.net.URI;
import java.util.List;

@Tag(name = "Diretores", description = "Operações de diretores")
@RestController @RequiredArgsConstructor
@RequestMapping("/api/diretores")
@CrossOrigin
public class DiretorController {
    private final DiretorService service;

    @Operation(summary = "Lista todos os diretores")
    @GetMapping public List<Diretor> all() { return service.findAll(); }

    @Operation(summary = "Busca diretor por ID")
    @ApiResponse(responseCode = "404", description = "Diretor não encontrado")
    @GetMapping("/{id}") public Diretor one(@Parameter(description = "ID do diretor") @PathVariable Long id) { return service.findById(id); }

    @Operation(summary = "Cria diretor")
    @PostMapping public ResponseEntity<Diretor> create(@RequestBody Diretor diretor) {
        Diretor saved = service.save(diretor);
        return ResponseEntity.created(URI.create("/api/diretores/" + saved.getId())).body(saved);
    }
    @Operation(summary = "Atualiza diretor por ID")
    @PutMapping("/{id}") public ResponseEntity<Diretor> update(@Parameter(description = "ID do diretor") @PathVariable Long id, @RequestBody Diretor diretor) {
        diretor.setId(id); return ResponseEntity.ok(service.save(diretor));
    }

    @Operation(summary = "Deleta diretor por ID")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "id", description = "ID do diretor a ser deletado") @PathVariable("id") Long id) { service.deleteById(id); }
}
