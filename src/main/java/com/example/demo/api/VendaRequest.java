package com.example.demo.api;

import com.example.demo.business.models.Item;
import com.example.demo.business.models.Venda;

import java.util.List;

public class VendaRequest {
    private Venda venda;
    private List<Item> items;

    public VendaRequest(Venda venda) {
        this.venda = venda;
    }

    public VendaRequest(Venda venda, List<Item> items) {
        this.venda = venda;
        this.items = items;
    }

    public VendaRequest() {
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "VendaRequest{" + venda.toString() + items.toString() +
                '}';
    }
}
