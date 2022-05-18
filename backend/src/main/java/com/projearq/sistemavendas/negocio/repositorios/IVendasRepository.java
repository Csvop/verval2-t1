package com.projearq.sistemavendas.negocio.repositorios;

import com.projearq.sistemavendas.negocio.entidades.Venda;

import java.util.List;

public interface IVendasRepository {

	void salvar(Venda venda);

	List<Venda> consultaVendas();

}
