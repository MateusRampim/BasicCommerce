package com.example.demo.webservice;

import com.example.demo.api.VendaBuscar;
import com.example.demo.api.VendaRequest;
import com.example.demo.business.models.Venda;
import com.example.demo.business.services.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name="Vendas")
@RestController
@RequestMapping({"/api/v1/vendas"})
public class VendasController {
    @Autowired
    VendaService vendaService;

    @PostMapping
    public Venda criar(@RequestBody VendaRequest vendaRequest) {
        System.out.println(vendaRequest.toString());
        return vendaService.criar(vendaRequest);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        System.out.println(id);
        vendaService.excluir(id);
    }

    @GetMapping
    public List<VendaRequest> buscar(@ModelAttribute VendaBuscar venda) {
        return vendaService.buscar(venda);
    }
}
