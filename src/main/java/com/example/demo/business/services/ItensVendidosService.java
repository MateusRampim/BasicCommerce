package com.example.demo.business.services;

import com.example.demo.business.models.Item;
import com.example.demo.business.models.ItensVendidos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItensVendidosService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void criar(UUID id_venda, UUID id_item) {
        String itemQuery = "INSERT INTO venda_item (id,venda_id, item_vendido_id) VALUES (uuid_generate_v4(),:venda, :item_id)";
        entityManager.createNativeQuery(itemQuery, ItensVendidos.class)
                .setParameter("venda", id_venda)
                .setParameter("item_id", id_item)
                .executeUpdate();

    }

    @Transactional
    public void deletar(UUID id_venda) {
        String query = "DELETE FROM venda_item WHERE venda_id = :id";
        entityManager.createNativeQuery(query, VendaService.class).setParameter("id", id_venda).executeUpdate();
    }

    @Transactional
    public List buscar(UUID vend_id) {
        String query = "SELECT i.* FROM venda_item vi JOIN itens i ON vi.item_vendido_id = i.id  WHERE venda_id = :id";
        return entityManager.createNativeQuery(query, Item.class).setParameter("id", vend_id).getResultList();
    }
}
