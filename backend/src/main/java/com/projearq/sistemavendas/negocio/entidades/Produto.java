package com.projearq.sistemavendas.negocio.entidades;

import javax.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
    @SequenceGenerator(allocationSize = 1, name = "id_produto_seq", sequenceName = "id_produto_seq")
    @GeneratedValue(generator = "id_produto_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_produto")
    private Long id;

	private Long codigo;

	private String descricao;

	private double precoUnitario;

    public Produto() {}

    public static class Builder {
		private Long id = null;
        private Long codigo = null;
        private String descricao = null;
        private double precoUnitario = 0.0;

        public Builder() {}

        public Builder codigo(Long codigo) {this.codigo = codigo; return this;}

        public Builder descricao(String descricao) {this.descricao = descricao; return this;}

        public Builder precoUnitario(double precoUnitario) {this.precoUnitario = precoUnitario; return this;}

        public Produto build() {return new Produto(this);}
    }
	private Produto(Builder builder) {
		this.id = builder.id;
		this.codigo = builder.codigo;
		this.descricao = builder.descricao;
		this.precoUnitario = builder.precoUnitario;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

}