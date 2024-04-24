package com.example.demo.business.services;

import com.example.demo.api.VendaBuscar;
import com.example.demo.api.VendaRequest;
import com.example.demo.business.models.Item;
import com.example.demo.business.models.Venda;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VendaService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ItensVendidosService itensVendidosService;

    @Transactional
    public Venda criar(VendaRequest vendaRequest) {
        String query = "INSERT INTO venda (id, vendedor_id, dia, total) VALUES (uuid_generate_v4(), :vendedor, CURRENT_DATE, :total) RETURNING *";

        Venda venda = (Venda) entityManager.createNativeQuery(query, Venda.class)
                .setParameter("vendedor", vendaRequest.getVenda().getVendedor().getId())
                .setParameter("total", vendaRequest.getVenda().getTotal())
                .getSingleResult();


        for (Item item : vendaRequest.getItems()) {
            itensVendidosService.criar(venda.getId(), item.getId());

        }

        return venda;
    }

    @Transactional
    public void excluir(UUID id) {
        itensVendidosService.deletar(id);
        String query = "DELETE FROM venda WHERE id = :id";
        entityManager.createNativeQuery(query).setParameter("id",id).executeUpdate();

    }

    @Transactional
    public List<VendaRequest> buscar(VendaBuscar venda) {
        List<VendaRequest> vendaRequests = new ArrayList<>();
        if (venda.getId() != null) {
            VendaRequest vendaRequest = new VendaRequest();
            String query = "SELECT * FROM venda WHERE id = :id";
            vendaRequest.setVenda((Venda) entityManager.createNativeQuery(query, Venda.class)
                    .setParameter("id", (UUID) venda.getId())
                    .getSingleResult());

            vendaRequest.setItems(itensVendidosService.buscar(vendaRequest.getVenda().getId()));
            vendaRequests.add(vendaRequest);
            return vendaRequests;

        } else if (venda.getInicio() != null && venda.getFim() != null) {
            String query = "SELECT * FROM venda WHERE dia BETWEEN :inicio AND :fim";
            List<Venda> vendas;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println(Date.valueOf(LocalDate.parse(venda.getFim(), formatter)));
            vendas = entityManager.createNativeQuery(query, Venda.class)
                    .setParameter("inicio", Date.valueOf(LocalDate.parse(venda.getInicio(), formatter)))
                    .setParameter("fim", Date.valueOf(LocalDate.parse(venda.getFim(), formatter)))
                    .getResultList();
            for (Venda venda1 : vendas) {
                vendaRequests.add(new VendaRequest(venda1, itensVendidosService.buscar(venda1.getId())));
            }
            return vendaRequests;
        }
        String query = "SELECT * FROM venda";
        List<Venda> vendas;
        vendas = entityManager.createNativeQuery(query, Venda.class)
                .getResultList();
        for (Venda venda1 : vendas) {
            vendaRequests.add(new VendaRequest(venda1, itensVendidosService.buscar(venda1.getId())));
        }
        return vendaRequests;
    }

}
