package com.projearq.sistemavendas.aplicacao.casosDeUso;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultaProdutosUC {

	private ServicoDeProduto servicoDeProduto;

	@Autowired
	public ConsultaProdutosUC(ServicoDeProduto servicoDeProduto) {
		this.servicoDeProduto = servicoDeProduto;
	}

	public List<ProdutoDTO> run() {
		return this.servicoDeProduto.consultaProdutos();
	}

}
