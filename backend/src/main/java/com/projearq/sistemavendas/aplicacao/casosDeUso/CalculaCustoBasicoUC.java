package com.projearq.sistemavendas.aplicacao.casosDeUso;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.aplicacao.servicos.restricoes.RestricoesFactory;
import com.projearq.sistemavendas.negocio.servicos.ServicoVendas;
import com.projearq.sistemavendas.negocio.strategy.ICalculoImpostoStrategy;
import com.projearq.sistemavendas.negocio.strategy.IRestricoesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculaCustoBasicoUC {

	private ServicoVendas servicoVendas;
	private RestricoesFactory restricoesFactory;
	private ICalculoImpostoStrategy calculoImpostoStrategy;

	@Autowired
	public CalculaCustoBasicoUC(ServicoVendas servicoVendas, RestricoesFactory restricoesFactory, ICalculoImpostoStrategy calculoImpostoStrategy) {
		this.servicoVendas = servicoVendas;
		this.restricoesFactory = restricoesFactory;
		this.calculoImpostoStrategy = calculoImpostoStrategy;
	}

	public Double[] run(List<ProdutoDTO> itens) {
		IRestricoesStrategy restricoes = this.restricoesFactory.restricoes();
		double subtotal = 0;
		double imposto = 0;
		subtotal = this.servicoVendas.calculaSubtotal(itens, restricoes, subtotal);
		imposto = this.calculoImpostoStrategy.calculaValorDoImposto(itens, subtotal);
		final Double[] resp = new Double[3];
		resp[0] = subtotal;
		resp[1] = imposto;
		resp[2] = subtotal + imposto;
		this.servicoVendas.validaRestricoesVenda(itens.size(), restricoes, resp[2]);
		return resp;
	}

}
