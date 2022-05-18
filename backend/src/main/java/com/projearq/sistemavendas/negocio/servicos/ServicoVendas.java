package com.projearq.sistemavendas.negocio.servicos;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.aplicacao.excecoes.ExcecaoDeNegocio;
import com.projearq.sistemavendas.aplicacao.servicos.restricoes.RestricoesFactory;
import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.ItemVenda;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.entidades.Venda;
import com.projearq.sistemavendas.negocio.repositorios.IVendasRepository;
import com.projearq.sistemavendas.negocio.strategy.ICalculoImpostoStrategy;
import com.projearq.sistemavendas.negocio.strategy.IRestricoesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ServicoVendas {

	private IVendasRepository vendasRepository;

	private ICalculoImpostoStrategy calculoImpostoStrategy;

	private RestricoesFactory restricoesFactory;

	private ServicoDeProduto servicoDeProduto;

	private  ServicoDeEstoque servicoDeEstoque;

	@Autowired
	public ServicoVendas(IVendasRepository vendasRepository, ICalculoImpostoStrategy calculoImpostoStrategy, RestricoesFactory restricoesFactory, ServicoDeProduto servicoDeProduto, ServicoDeEstoque servicoDeEstoque) {
		this.vendasRepository = vendasRepository;
		this.calculoImpostoStrategy = calculoImpostoStrategy;
		this.restricoesFactory = restricoesFactory;
		this.servicoDeProduto = servicoDeProduto;
		this.servicoDeEstoque = servicoDeEstoque;
	}

	public List<Venda> consultaVendas() {
		return this.vendasRepository.consultaVendas();
	}

	public void validaRestricoesVenda(int quantidadeItens, IRestricoesStrategy restricoes, double totalVenda) {
		if (restricoes != null && restricoes.restringeQuantidadeItensVenda(quantidadeItens))
			throw new ExcecaoDeNegocio("Restrição na quantidade total de itens da venda!");
		if (restricoes != null && restricoes.restringeValorTotalVenda(totalVenda))
			throw new ExcecaoDeNegocio("Restrição no total da venda!");
	}

	public double calculaSubtotal(List<ProdutoDTO> itens, IRestricoesStrategy restricoes, double subtotal) {
		for (ProdutoDTO item : itens) {
			if (restricoes != null && restricoes.restringeQuantidadeItem(item.getQuantidade()))
				throw new ExcecaoDeNegocio("Restrição na quantidade para esse item!");
			Produto produto = this.servicoDeProduto.buscaProduto(item.getCodigo());
			subtotal += produto.getPrecoUnitario() * item.getQuantidade();
		}
		return subtotal;
	}

	public void salvarVenda(List<ItemVenda> itensVenda) {
		Venda venda = new Venda.Builder()
				.data(new Date())
				.itensVenda(itensVenda)
				.build();
		this.vendasRepository.salvar(venda);
	}

	public boolean verificaDisponibilidade(Long codProd, Integer qtdade) {
		Estoque estoque = this.servicoDeEstoque.buscaItemEstoque(codProd);
		return estoque.getQuantidadeDisponivel() >= qtdade;
	}

}
