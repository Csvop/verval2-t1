package com.projearq.sistemavendas.adaptadores.configuradores;

import com.projearq.sistemavendas.aplicacao.servicos.calculoImposto.CalculoImpostoArgentina;
import com.projearq.sistemavendas.aplicacao.servicos.calculoImposto.CalculoImpostoBrasil;
import com.projearq.sistemavendas.negocio.strategy.ICalculoImpostoStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracoesCalculoImposto {

    @Bean
    @ConditionalOnProperty(name = "REGRA.IMPOSTO", havingValue = "brasil", matchIfMissing = true)
    public ICalculoImpostoStrategy opcaoBrasil() {
        return new CalculoImpostoBrasil();
    }

    @Bean
    @ConditionalOnProperty(name = "REGRA.IMPOSTO", havingValue = "argentina")
    public ICalculoImpostoStrategy opcaoArgentina() {
        return new CalculoImpostoArgentina();
    }

}
