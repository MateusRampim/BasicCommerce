package com.example.demo.webservice;

import com.example.demo.api.VendaBuscar;
import com.example.demo.api.VendaRequest;
import com.example.demo.business.models.Item;
import com.example.demo.business.models.ItensVendidos;
import com.example.demo.business.models.Venda;
import com.example.demo.business.repository.ItemRepository;
import com.example.demo.business.repository.VendaItemRepository;
import com.example.demo.business.repository.VendaRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Vendas")
@RestController
@RequestMapping({"/api/v1/vendas"})
public class VendasController {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaItemRepository vendaItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequest vendaRequest) {
        // Obtém a venda do VendaRequest
        Venda venda = vendaRequest.getVenda();

        // Salva a venda
        Venda novaVenda = vendaRepository.save(venda);

        // Cria uma entrada na tabela venda_item para cada item vendido
        for (Item item : vendaRequest.getItems()) {
            ItensVendidos vendaItem = new ItensVendidos();
            vendaItem.setVenda_id(novaVenda.getId());
            vendaItem.setItem_vendido_id(item.getId()); // Suponho que você tenha o ID do item disponível
            vendaItemRepository.save(vendaItem);
        }

        return new ResponseEntity<>(novaVenda, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaRequest> getVendaById(@PathVariable UUID id) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda == null) {
            return ResponseEntity.notFound().build();
        }

        List<UUID> itemIds = vendaItemRepository.findItemIdsByVendaId(id);
        List<Item> itensVendidos = itemRepository.findAllById(itemIds);

        VendaRequest vendaRequest = new VendaRequest(venda, itensVendidos);

        return ResponseEntity.ok(vendaRequest);
    }
    @GetMapping()
    public ResponseEntity<List<Venda>> getVendas() {
        List<Venda> venda = vendaRepository.findAll();
        if (venda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(venda);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable UUID id) {
        if (!vendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vendaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
