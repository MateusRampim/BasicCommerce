package com.example.demo.business.repository;

import com.example.demo.business.models.Cliente;
import com.example.demo.business.models.Item;
import com.example.demo.business.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

}
