package com.example.demo.business.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int estoque;

    @Column(nullable = false)
    private BigDecimal valor;

    public Item() {
    }

    public Item(String nome, int estoque, BigDecimal valor) {
        this.nome = nome;
        this.estoque = estoque;
        this.valor = valor;
    }

    // Getters e Setters

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", estoque=" + estoque +
                ", valor=" + valor +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
