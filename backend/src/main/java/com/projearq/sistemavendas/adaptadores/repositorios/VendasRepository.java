package com.projearq.sistemavendas.adaptadores.repositorios;

import com.projearq.sistemavendas.adaptadores.repositorios.interfaces.IVendasRepositoryCustom;
import com.projearq.sistemavendas.negocio.repositorios.IVendasRepository;
import com.projearq.sistemavendas.negocio.entidades.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendasRepository implements IVendasRepository {

	private IVendasRepositoryCustom vendasRepositoryCustom;

	@Autowired
	public VendasRepository(IVendasRepositoryCustom vendasRepositoryCustom) {
		this.vendasRepositoryCustom = vendasRepositoryCustom;
	}

	@Override
	public void salvar(Venda venda) {
		this.vendasRepositoryCustom.save(venda);
	}

	@Override
	public List<Venda> consultaVendas() {
		return this.vendasRepositoryCustom.findAll();
	}

}
