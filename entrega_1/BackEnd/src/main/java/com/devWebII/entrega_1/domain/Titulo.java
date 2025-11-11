package com.devWebII.entrega_1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Titulo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String nome;

    private Integer ano;

    @ManyToOne @JoinColumn(name = "classe_id")
    @NotNull
    private Classe classe;

    @ManyToMany
    @JoinTable(name = "titulo_ator",
       joinColumns = @JoinColumn(name = "titulo_id"),
       inverseJoinColumns = @JoinColumn(name = "ator_id"))
    @Builder.Default @JsonIgnoreProperties("titulos")
    private Set<Ator> atores = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "titulo_diretor",
       joinColumns = @JoinColumn(name = "titulo_id"),
       inverseJoinColumns = @JoinColumn(name = "diretor_id"))
    @Builder.Default @JsonIgnoreProperties("titulos")
    private Set<Diretor> diretores = new HashSet<>();
}
