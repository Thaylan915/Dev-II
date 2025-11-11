package com.devWebII.entrega_1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties("titulos")
public class Diretor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String nome;

    @ManyToMany(mappedBy = "diretores")
    @Builder.Default
    private Set<Titulo> titulos = new HashSet<>();
}
