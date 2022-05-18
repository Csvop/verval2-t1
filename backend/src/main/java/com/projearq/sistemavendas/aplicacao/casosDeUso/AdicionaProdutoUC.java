package com.projearq.sistemavendas.aplicacao.casosDeUso;

import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdicionaProdutoUC {

    ServicoDeProduto servicoDeProduto;

    @Autowired
    public AdicionaProdutoUC(ServicoDeProduto servicoDeProduto) {
        this.servicoDeProduto = servicoDeProduto;
    }

    public Produto run(Produto produto) {
        return this.servicoDeProduto.adicionaProduto(produto);
    }
}
