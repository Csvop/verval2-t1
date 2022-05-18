package com.projearq.sistemavendas.adaptadores.repositorios.interfaces;

import com.projearq.sistemavendas.negocio.entidades.Estoque;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstoqueRepositoryCustom extends CrudRepository<Estoque, Long> {

    Estoque findByProduto_Codigo(Long codigo);

}
