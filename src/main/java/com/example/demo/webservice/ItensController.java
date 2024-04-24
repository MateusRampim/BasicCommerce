package com.example.demo.webservice;

import com.example.demo.business.models.Item;

import com.example.demo.business.repository.ItemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name="Itens")
@RestController
@RequestMapping("/api/v1/itens")
public class ItensController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable UUID id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> criarItem(@RequestBody Item item) {
        Item novoItem = itemRepository.save(item);
        return new ResponseEntity<>(novoItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizarItem(@PathVariable UUID id, @RequestBody Item itemAtualizado) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza os campos do item
        item.setNome(itemAtualizado.getNome());
        item.setEstoque(itemAtualizado.getEstoque());
        item.setValor(itemAtualizado.getValor());

        Item itemAtualizadoBanco = itemRepository.save(item);
        return ResponseEntity.ok(itemAtualizadoBanco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable UUID id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItens() {
        List<Item> itens = itemRepository.findAll();
        return ResponseEntity.ok(itens);
    }
}
