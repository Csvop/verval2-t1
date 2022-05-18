package com.projearq.sistemavendas.aplicacao.casosDeUso;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.entidades.ItemVenda;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeEstoque;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeProduto;
import com.projearq.sistemavendas.negocio.servicos.ServicoVendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfirmaVendaUC {

    private ServicoVendas servicoVendas;
    private ServicoDeEstoque servicoDeEstoque;
    private ServicoDeProduto servicoDeProduto;
    private CalculaCustoBasicoUC calculaCustoBasicoUC;

    @Autowired
    public ConfirmaVendaUC(ServicoVendas servicoVendas, ServicoDeEstoque servicoDeEstoque, ServicoDeProduto servicoDeProduto, CalculaCustoBasicoUC calculaCustoBasicoUC) {
        this.servicoVendas = servicoVendas;
        this.servicoDeEstoque = servicoDeEstoque;
        this.servicoDeProduto = servicoDeProduto;
        this.calculaCustoBasicoUC = calculaCustoBasicoUC;
    }

    public boolean run(List<ProdutoDTO> itens) {
        List<ItemVenda> itensVenda = new ArrayList<>();
        Double[] subtotais = this.calculaCustoBasicoUC.run(itens);
        for (ProdutoDTO item : itens) {
            this.servicoDeEstoque.diminuiQuantidadeItemEstoque(item.getCodigo(), item.getQuantidade());
            Produto produto = this.servicoDeProduto.buscaProduto(item.getCodigo());
            ItemVenda itemVenda = new ItemVenda.Builder()
            .quantidade(item.getQuantidade())
            .precoUnitVenda(subtotais[0])
            .imposto(subtotais[1])
            .produto(produto)
            .build();
            itensVenda.add(itemVenda);
        }
        this.servicoVendas.salvarVenda(itensVenda);
        return true;
    }

}
