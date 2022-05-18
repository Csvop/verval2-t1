package com.projearq.sistemavendas.adaptadores.controllers;

import com.projearq.sistemavendas.aplicacao.casosDeUso.*;
import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.entidades.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendasController {

	private ConsultaVendasUC consultaVendasUC;

	private CalculaCustoBasicoUC calculaCustoBasicoUC;

	private ConfirmaVendaUC confirmaVendaUC;

	private VerificaDisponibilidadeUC verificaDisponibilidadeUC;

	@Autowired
	public VendasController(ConsultaVendasUC consultaVendasUC, CalculaCustoBasicoUC calculaCustoBasicoUC, ConfirmaVendaUC confirmaVendaUC, VerificaDisponibilidadeUC verificaDisponibilidadeUC) {
		this.consultaVendasUC = consultaVendasUC;
		this.calculaCustoBasicoUC = calculaCustoBasicoUC;
		this.confirmaVendaUC = confirmaVendaUC;
		this.verificaDisponibilidadeUC = verificaDisponibilidadeUC;
	}

	@GetMapping("/autorizacao")
	@CrossOrigin(origins = "*")
	public boolean podeVender(@RequestParam final Long codProd, @RequestParam final Integer qtdade) {
		return this.verificaDisponibilidadeUC.run(codProd, qtdade);
	}

	@PostMapping("/confirmacao")
	@CrossOrigin(origins = "*")
	public boolean confirmaVenda(@RequestBody final List<ProdutoDTO> itens) throws Exception {
		return this.confirmaVendaUC.run(itens);
	}

	@GetMapping("/historico")
	@CrossOrigin(origins = "*")
	public List<Venda> vendasEfetuadas() {
		return this.consultaVendasUC.run();
	}

	@PostMapping("/subtotal")
	@CrossOrigin(origins = "*")
	public Double[] calculaSubtotal(@RequestBody final List<ProdutoDTO> itens) throws Exception {
		return this.calculaCustoBasicoUC.run(itens);
	}

}
