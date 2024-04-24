package com.example.demo.webservice;

import com.example.demo.business.models.Vendedor;
import com.example.demo.business.services.VendedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name="Vendedor",description = "Vendedor")
@RestController
@RequestMapping("/api/v1/vendedores")
public class VendedorController {
    @Autowired
    VendedorService vendedorService;
    @PersistenceContext
    private EntityManager entityManager;

    @Operation()
    @PostMapping
    public Vendedor cadastrarVendedor(@RequestBody Vendedor vend) {
        return vendedorService.criar(vend);
    }

    @DeleteMapping("/{id}")
    public void excluirVendedor(@PathVariable UUID id) {
        vendedorService.excluir(id);
    }

    @GetMapping
    public List<Vendedor> buscarVendedor(@ModelAttribute Vendedor vendedor) {
        return vendedorService.buscar(vendedor);
    }

    @PutMapping
    public Vendedor editar(@RequestBody Vendedor vendedor) {
        return vendedorService.editar(vendedor);
    }


}
