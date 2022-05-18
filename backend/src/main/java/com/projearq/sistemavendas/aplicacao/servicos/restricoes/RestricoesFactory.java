package com.projearq.sistemavendas.aplicacao.servicos.restricoes;

import com.projearq.sistemavendas.negocio.strategy.IRestricoesStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RestricoesFactory {

    private final LocalTime HORARIO_FECHAMENTO = LocalTime.of(19,0,0);

    public IRestricoesStrategy restricoes() {
        if (LocalTime.now().isAfter(HORARIO_FECHAMENTO)) {
            return new RestricoesNivelBaixo();
        } else {
            return null;
        }
    }

}
