package com.projearq.sistemavendas.negocio.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "id_venda_seq", sequenceName = "id_venda_seq")    
    @GeneratedValue(generator = "id_venda_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_venda")
    private Long id;
	private Long numero;
	private Date data;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "venda")
	private List<ItemVenda> itensVenda;

    public Venda() {}

    public static class Builder {

        private Long id = null;
        private Long numero = null;
        private Date data = null;
        private List<ItemVenda> itensVenda = null;
        
        public Builder() {}

        public Builder numero(Long numero) {this.numero = numero; return this;}

        public Builder data(Date data) {this.data = data; return this;}

        public Builder itensVenda(List<ItemVenda> itensVenda) {this.itensVenda = itensVenda; return this;}

        public Venda build() {return new Venda(this);}
    }

    private Venda(Builder builder) {
        this.id = builder.id;
        this.numero = builder.numero;
        this.data = builder.data;
        this.itensVenda = builder.itensVenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

}
