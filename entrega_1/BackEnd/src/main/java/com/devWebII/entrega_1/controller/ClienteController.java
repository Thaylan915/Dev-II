package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.domain.Cliente;
import com.devWebII.entrega_1.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Operações de clientes")
@RestController @RequiredArgsConstructor
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClienteController {
    private final ClienteService service;

    @Operation(summary = "Lista clientes")
    @GetMapping public List<Cliente> all() { return service.findAll(); }

    @Operation(summary = "Busca cliente por ID")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping("/{id}") public ResponseEntity<Cliente> one(@PathVariable Long id) {
        Cliente cliente = service.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Cria cliente")
    @PostMapping public ResponseEntity<Cliente> create(@RequestBody Cliente c) {
        Cliente saved = service.save(c);
        return ResponseEntity.created(URI.create("/api/clientes/" + saved.getId())).body(saved);
    }

    @Operation(summary = "Atualiza cliente por ID")
    @PutMapping("/{id}") public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente c) {
        c.setId(id); return ResponseEntity.ok(service.save(c));
    }

    @Operation(summary = "Deleta cliente por ID")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }

    @Operation(summary = "Lista clientes sócios")
    @GetMapping("/socios") public List<Cliente> socios() { return service.findSocios(); }
}
