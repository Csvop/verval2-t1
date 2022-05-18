package com.projearq.sistemavendas.adaptadores.controllers;

import com.projearq.sistemavendas.aplicacao.casosDeUso.AdicionaProdutoUC;
import com.projearq.sistemavendas.aplicacao.casosDeUso.ConsultaProdutosUC;
import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

	public ProdutosController() {}

	private ConsultaProdutosUC consultaProdutosUC;
	private AdicionaProdutoUC adicionaProdutoUC;

	@Autowired
	public ProdutosController(ConsultaProdutosUC consultaProdutosUC, AdicionaProdutoUC adicionaProdutoUC) {
		this.consultaProdutosUC = consultaProdutosUC;
		this.adicionaProdutoUC = adicionaProdutoUC;
	}

	@GetMapping
	@CrossOrigin(origins = "*")
	public List<ProdutoDTO> consultaProdutos() {
		return this.consultaProdutosUC.run();
	}

	@PostMapping
	@CrossOrigin(origins = "*")
	public Produto adicionaProduto(@RequestBody Produto produto) {
		return this.adicionaProdutoUC.run(produto);
	}

}
