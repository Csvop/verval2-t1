package com.projearq.sistemavendas.adaptadores.repositorios.interfaces;

import com.projearq.sistemavendas.negocio.entidades.Venda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVendasRepositoryCustom extends CrudRepository<Venda, Long> {

    Venda findByNumero(int numero);

    List<Venda> findAll();

}
