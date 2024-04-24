package com.example.demo.business.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "venda_item")
public class ItensVendidos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "venda_id")
    private UUID venda_id;

    @Column(name = "item_vendido_id")
    private UUID item_vendido_id;

    public ItensVendidos() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVenda_id() {
        return venda_id;
    }

    public void setVenda_id(UUID venda_id) {
        this.venda_id = venda_id;
    }

    public UUID getItem_vendido_id() {
        return item_vendido_id;
    }

    public void setItem_vendido_id(UUID item_vendido_id) {
        this.item_vendido_id = item_vendido_id;
    }
}
