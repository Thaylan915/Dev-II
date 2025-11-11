package com.devWebII.entrega_1.repository;

import com.devWebII.entrega_1.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> { }
