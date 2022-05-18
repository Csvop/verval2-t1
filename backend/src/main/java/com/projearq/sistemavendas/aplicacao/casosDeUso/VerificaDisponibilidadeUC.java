package com.projearq.sistemavendas.aplicacao.casosDeUso;

import com.projearq.sistemavendas.negocio.servicos.ServicoVendas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificaDisponibilidadeUC {

    private ServicoVendas servicoVendas;

    @Autowired
    public VerificaDisponibilidadeUC(ServicoVendas servicoVendas) {
        this.servicoVendas = servicoVendas;
    }

    public boolean run(Long codProd, Integer qtdade) {
        return this.servicoVendas.verificaDisponibilidade(codProd, qtdade);
    }

}
