package com.projearq.sistemavendas.aplicacao.casosDeUso;

import com.projearq.sistemavendas.negocio.entidades.Venda;
import com.projearq.sistemavendas.negocio.servicos.ServicoVendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultaVendasUC {

	private ServicoVendas servicoVendas;

	@Autowired
	public ConsultaVendasUC(ServicoVendas servicoVendas) {
		this.servicoVendas = servicoVendas;
	}

	public List<Venda> run() {
		return this.servicoVendas.consultaVendas();
	}

}
