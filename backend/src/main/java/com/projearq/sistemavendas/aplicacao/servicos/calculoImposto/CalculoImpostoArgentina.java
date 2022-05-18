package com.projearq.sistemavendas.aplicacao.servicos.calculoImposto;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.strategy.ICalculoImpostoStrategy;

import java.util.List;

public class CalculoImpostoArgentina implements ICalculoImpostoStrategy {

    private final double VALOR_IMPOSTO_IVA = 0.1;

    private final double IMPOSTO_IVA_PRODUTO_10L = 0.25;

    private final double IMPOSTO_IVA_PRODUTO_30L = 0.15;

    @Override
    public Double calculaValorDoImposto(List<ProdutoDTO> itens, double subtotal) {

        double imposto = 0;
        
        for (ProdutoDTO product : itens) {

            if (product.getCodigo().equals(10L)) {
                imposto += product.getPreco() * IMPOSTO_IVA_PRODUTO_10L;
            } else if (product.getCodigo().equals(30L)) {
                imposto += product.getPreco() * IMPOSTO_IVA_PRODUTO_30L;
            } else {
                imposto += product.getPreco() * VALOR_IMPOSTO_IVA;
            }
        }
        
        return imposto;
    }
}
