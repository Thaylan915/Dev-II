package com.devWebII.entrega_1.config;

import com.devWebII.entrega_1.domain.*;
import com.devWebII.entrega_1.domain.enums.*;
import com.devWebII.entrega_1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final ClasseRepository classeRepo;
    private final AtorRepository atorRepo;
    private final DiretorRepository diretorRepo;
    private final TituloRepository tituloRepo;
    private final ItemRepository itemRepo;
    private final ClienteRepository clienteRepo;

    @Override public void run(String... args) {
        if (classeRepo.count() > 0) return;

        Classe c = classeRepo.save(Classe.builder().nome("Aventura").build());
        Ator a = atorRepo.save(Ator.builder().nome("Ator Exemplo").build());
        Diretor d = diretorRepo.save(Diretor.builder().nome("Diretor Exemplo").build());

        Titulo t = Titulo.builder().nome("Filme Demo").ano(2024).classe(c).build();
        t.getAtores().add(a);
        t.getDiretores().add(d);
        t = tituloRepo.save(t);

        itemRepo.save(Item.builder()
                .numeroSerie("ITEM-001")
                .titulo(t)
                .tipo(TipoItem.DVD)
                .dataAquisicao(LocalDate.now())
                .build());

        clienteRepo.save(Cliente.builder()
                .nome("Cliente Demo")
                .sexo(Sexo.MASCULINO)
                .cpf("000.000.000-00")
                .ativo(true)
                .tipo(TipoCliente.SOCIO)
                .build());
    }
}
