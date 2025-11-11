package com.devWebII.entrega_1.repository;

import com.devWebII.entrega_1.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
    boolean existsByAtoresId(Long atorId);
    boolean existsByDiretoresId(Long diretorId);
    boolean existsByClasseId(Long classeId);
}
