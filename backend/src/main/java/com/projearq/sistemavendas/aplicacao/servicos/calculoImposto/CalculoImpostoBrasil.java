package com.projearq.sistemavendas.aplicacao.servicos.calculoImposto;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.strategy.ICalculoImpostoStrategy;

import java.util.List;

public class CalculoImpostoBrasil implements ICalculoImpostoStrategy {

    private final double VALOR_IMPOSTO_IVA = 0.12;

    private final double VALOR_LIMITE_COMPRA_PARA_IMPOSTO_IVA = 8000;

    private final double VALOR_IMPOSTO_IVA_COM_VALOR_LIMITE_EXCEDENTE = 0.2;

    @Override
    public Double calculaValorDoImposto(List<ProdutoDTO> itens, double subtotal) {
        double imposto;

        if (subtotal <= VALOR_LIMITE_COMPRA_PARA_IMPOSTO_IVA) {
            imposto = subtotal * VALOR_IMPOSTO_IVA;
        } else {
            imposto = subtotal * VALOR_IMPOSTO_IVA_COM_VALOR_LIMITE_EXCEDENTE;
        }
        
        return imposto;
    }
}
