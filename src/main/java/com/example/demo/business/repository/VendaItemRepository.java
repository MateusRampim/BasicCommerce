package com.example.demo.business.repository;


import com.example.demo.business.models.ItensVendidos;
import com.example.demo.business.models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VendaItemRepository extends JpaRepository<ItensVendidos, UUID> {

    @Query(value = "SELECT item_vendido_id FROM venda_item WHERE venda_id = :vendaId", nativeQuery = true)
    List<UUID> findItemIdsByVendaId(UUID vendaId);



}
