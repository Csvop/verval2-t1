package com.projearq.sistemavendas.negocio.servicos;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.repositorios.IProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServicoDeProduto {

	private IProdutosRepository produtosRepository;
	private ServicoDeEstoque servicoDeEstoque;

	@Autowired
	public ServicoDeProduto(IProdutosRepository produtosRepository, ServicoDeEstoque servicoDeEstoque) {
		this.produtosRepository = produtosRepository;
		this.servicoDeEstoque = servicoDeEstoque;
	}

	public List<ProdutoDTO> consultaProdutos() {
		List<ProdutoDTO> produtosComQuantidade = new ArrayList<>();
		List<Produto> produtos = this.produtosRepository.consultaProdutos();
		for (Produto produto : produtos) {
			Estoque estoque = this.servicoDeEstoque.buscaItemEstoque(produto.getCodigo());
			produtosComQuantidade.add(new ProdutoDTO(produto.getCodigo(), produto.getDescricao(), produto.getPrecoUnitario(), estoque.getQuantidadeDisponivel()));
		}
		return produtosComQuantidade;
	}

	public Produto buscaProduto(Long codigo) {
		return this.produtosRepository.buscarProduto(codigo);
	}

	public Produto adicionaProduto(Produto produto) {
		return this.produtosRepository.adicionaProduto(produto);
	}

}
