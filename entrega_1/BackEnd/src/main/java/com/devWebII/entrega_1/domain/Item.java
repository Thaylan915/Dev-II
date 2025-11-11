package com.devWebII.entrega_1.domain;

import com.devWebII.entrega_1.domain.enums.TipoItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String numeroSerie;

    @ManyToOne(optional=false) @JoinColumn(name="titulo_id")
    private Titulo titulo;

    private LocalDate dataAquisicao;

    @Enumerated(EnumType.STRING)
    private TipoItem tipo;
}
