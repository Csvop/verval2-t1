package com.projearq.sistemavendas.adaptadores.repositorios;

import com.projearq.sistemavendas.adaptadores.repositorios.interfaces.IProdutosRepositoryCustom;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.repositorios.IProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutosRepository implements IProdutosRepository {

	private IProdutosRepositoryCustom produtosRepositoryCustom;

	@Autowired
	public ProdutosRepository(IProdutosRepositoryCustom produtosRepositoryCustom) {
		this.produtosRepositoryCustom = produtosRepositoryCustom;
	}

	@Override
	public List<Produto> consultaProdutos() {
		return this.produtosRepositoryCustom.findAll();
	}

	@Override
	public Produto buscarProduto(Long codigo) {
		return this.produtosRepositoryCustom.findByCodigo(codigo);
	}

	@Override
	public Produto adicionaProduto(Produto produto) {
		return this.produtosRepositoryCustom.save(produto);
	}
}
