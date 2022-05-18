package com.projearq.sistemavendas.negocio.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "itens_venda")
public class ItemVenda {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "id_itens_venda_seq", sequenceName = "id_itens_venda_seq")
    @GeneratedValue(generator = "id_itens_venda_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_itens_venda")
	private Long id;

	private double quantidade;

	private double precoUnitVenda;

	private double imposto;

    @OneToOne
    @JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_venda", nullable = false)
	private Venda venda;

    public ItemVenda() {}

    public static class Builder {
        private Long id = null;
        private double quantidade = 0.0;
        private double precoUnitVenda = 0.0;
        private double imposto = 0.0;
        private Produto produto = null;
        private Venda venda = null;

        public Builder() {}

        public Builder quantidade(double quantidade) {this.quantidade = quantidade; return this;}

        public Builder precoUnitVenda(double precoUnitVenda) {this.precoUnitVenda = precoUnitVenda; return this;}

        public Builder imposto(double imposto) {this.imposto = imposto; return this;}

        public Builder produto(Produto produto) {this.produto = produto; return this;}

        public Builder venda(Venda venda) {this.venda = venda; return this;}

        public ItemVenda build() {return new ItemVenda(this);}

    }

    private ItemVenda(Builder builder) {
        this.id = builder.id;
        this.quantidade = builder.quantidade;
        this.precoUnitVenda = builder.precoUnitVenda;
        this.imposto = builder.imposto;
        this.produto = builder.produto;
        this.venda = builder.venda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitVenda() {
        return precoUnitVenda;
    }

    public void setPrecoUnitVenda(double precoUnitVenda) {
        this.precoUnitVenda = precoUnitVenda;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

}
