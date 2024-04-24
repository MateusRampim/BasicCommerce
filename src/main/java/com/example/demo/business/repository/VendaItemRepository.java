package com.example.demo.business.repository;


import com.example.demo.business.models.ItensVendidos;
import com.example.demo.business.models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VendaItemRepository extends JpaRepository<ItensVendidos, UUID> {



}
