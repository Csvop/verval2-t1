package com.projearq.sistemavendas.adaptadores.repositorios.interfaces;

import com.projearq.sistemavendas.negocio.entidades.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdutosRepositoryCustom extends CrudRepository<Produto, Long> {

    List<Produto> findAll();

    Produto findByCodigo(Long codigo);

}
