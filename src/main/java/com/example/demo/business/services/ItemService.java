package com.example.demo.business.services;

import com.example.demo.business.models.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
 *
 *
 * */
@Service
public class ItemService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Item criar(Item item) {
        String query = "INSERT INTO itens (id, nome, estoque,valor) VALUES (uuid_generate_v4(),  :nome , :estoque,:valor ) RETURNING *";

        return (Item) entityManager.createNativeQuery(query, Item.class)
                .setParameter("nome", item.getNome())
                .setParameter("estoque", item.getEstoque()).setParameter("valor", item.getValor())
                .getSingleResult();
    }

    @Transactional
    public List<Item> buscar(Item item) {
        if (item != null) {
            if (item.getId() != null) {
                String query = "SELECT * FROM itens WHERE id = :id";
                return entityManager.createNativeQuery(query, Item.class).setParameter("id", item.getId()).getResultList();
            } else if (item.getNome() != null) {
                String query = "SELECT * FROM itens WHERE nome = :nome";
                return entityManager.createNativeQuery(query, Item.class).setParameter("nome", item.getNome()).getResultList();

            }
        }
        String query = "SELECT * FROM itens";
        return entityManager.createNativeQuery(query, Item.class).getResultList();
    }

    @Transactional
    public Item busca(UUID uuid) {
        String query = "SELECT * FROM itens WHERE id = :id";
        return (Item) entityManager.createNativeQuery(query, Item.class).setParameter("id", uuid).getSingleResult();
    }

    @Transactional
    public void excluir(UUID item) {
        if (item != null) {
            String query = "DELETE FROM itens WHERE id = :id";
            entityManager.createNativeQuery(query).setParameter("id", item).executeUpdate();
        }

    }

    @Transactional
    public Item editar(Item item) {
        if (item.getId() != null) {
            if (item.getNome() != null) {
                String query = "UPDATE Itens SET nome = :nome WHERE id = :id RETURNING *";
                return (Item) entityManager.createNativeQuery(query, Item.class).setParameter("id", item.getId()).setParameter("nome", item.getNome()).getSingleResult();
            } else if (item.getEstoque() != 0) {
                String query = "UPDATE Itens SET estoque = estoque + :estoque WHERE id = :id RETURNING *";
                return (Item) entityManager.createNativeQuery(query, Item.class).setParameter("id", item.getId()).setParameter("estoque", item.getEstoque()).getSingleResult();
            }
        }

        return null;
    }

}
