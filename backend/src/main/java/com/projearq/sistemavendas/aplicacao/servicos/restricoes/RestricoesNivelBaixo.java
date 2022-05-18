package com.projearq.sistemavendas.aplicacao.servicos.restricoes;

import com.projearq.sistemavendas.negocio.strategy.IRestricoesStrategy;
import org.springframework.stereotype.Component;

@Component
public class RestricoesNivelBaixo implements IRestricoesStrategy {

    private final int QUANTIDADE_MAXIMA_ITENS_VENDA = 10;
    private final int QUANTIDADE_MAXIMA_ITEM = 10;
    private final int VALOR_MAXIMA_TOTAL_VENDA = 10000;

    @Override
    public boolean restringeQuantidadeItensVenda(int quantidadeItensVenda) {
        return quantidadeItensVenda > this.QUANTIDADE_MAXIMA_ITENS_VENDA;
    }

    @Override
    public boolean restringeQuantidadeItem(int quantidadeItem) {
        return quantidadeItem > this.QUANTIDADE_MAXIMA_ITEM;
    }

    @Override
    public boolean restringeValorTotalVenda(double valorTotalVenda) {
        return valorTotalVenda > this.VALOR_MAXIMA_TOTAL_VENDA;
    }

}
