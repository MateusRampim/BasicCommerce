package com.example.demo.business.services;

import com.example.demo.business.models.Vendedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VendedorService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Vendedor criar(Vendedor vendedor) {
        String query = "INSERT INTO vendedor (id, nome,contato) VALUES (uuid_generate_v4(),  :nome , :contato ) RETURNING *";

        return (Vendedor) entityManager.createNativeQuery(query, Vendedor.class)
                .setParameter("nome", vendedor.getNome())
                .setParameter("contato", vendedor.getContato())
                .getSingleResult();
    }

    @Transactional
    public List<Vendedor> buscar(Vendedor vendedor) {
        if (vendedor != null) {
            if (vendedor.getId() != null) {
                String query = "SELECT * FROM vendedor WHERE id = :id";
                return entityManager.createNativeQuery(query, Vendedor.class).setParameter("id", vendedor.getId()).getResultList();
            } else if (vendedor.getNome() != null) {
                String query = "SELECT * FROM vendedor WHERE nome = :nome";
                return entityManager.createNativeQuery(query, Vendedor.class).setParameter("nome", vendedor.getNome()).getResultList();
            }
        }
        String query = "SELECT * FROM vendedor";
        return entityManager.createNativeQuery(query, Vendedor.class).getResultList();
    }

    @Transactional
    public void excluir(UUID vendedor) {
        if (vendedor != null) {
            String query = "DELETE FROM vendedor WHERE id = :id";
            entityManager.createNativeQuery(query).setParameter("id", vendedor).executeUpdate();
        }

    }

    @Transactional
    public Vendedor editar(Vendedor vendedor) {
        if (vendedor.getId() != null) {
            if (vendedor.getNome() != null) {
                String query = "UPDATE vendedor SET nome = :nome WHERE id = :id RETURNING *";
                return (Vendedor) entityManager.createNativeQuery(query, Vendedor.class).setParameter("id", vendedor.getId()).setParameter("nome", vendedor.getNome()).getSingleResult();
            } else if (vendedor.getContato() != null) {
                String query = "UPDATE vendedor SET contato = :contato WHERE id = :id RETURNING *";
                return (Vendedor) entityManager.createNativeQuery(query, Vendedor.class).setParameter("id", vendedor.getId()).setParameter("contato", vendedor.getContato()).getSingleResult();
            }
        }

        return null;
    }
}
