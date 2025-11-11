package com.devWebII.entrega_1.domain;

import com.devWebII.entrega_1.domain.enums.Sexo;
import com.devWebII.entrega_1.domain.enums.TipoCliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private String cpf;
    private LocalDate dataNascimento;
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipo;
}
