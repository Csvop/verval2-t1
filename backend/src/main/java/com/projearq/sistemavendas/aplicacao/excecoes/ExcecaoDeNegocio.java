package com.projearq.sistemavendas.aplicacao.excecoes;

public class ExcecaoDeNegocio extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExcecaoDeNegocio(String message) {
        super(message);
    }
}
