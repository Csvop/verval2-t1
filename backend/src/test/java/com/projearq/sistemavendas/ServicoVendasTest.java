package com.projearq.sistemavendas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeEstoque;
import com.projearq.sistemavendas.negocio.servicos.ServicoVendas;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServicoVendasTest {
    
    @InjectMocks
    private ServicoVendas servicoVendas;

    @Mock
    private ServicoDeEstoque servicoDeEstoque;

    @Test
    public void testVerificaDisponibilidadeTrue() {
        // arrange
        int quantidadeDisponivel = 10;
        Produto produto = new Produto.Builder().codigo(1l).descricao("").build();
        Estoque estoque = new Estoque.Builder().produto(produto).quantidadeDisponivel(quantidadeDisponivel).build();
        when(servicoDeEstoque.buscaItemEstoque(1l)).thenReturn(estoque);

        // act
        Boolean result = servicoVendas.verificaDisponibilidade(1l, 1);

        // assert
        assertTrue(result);
    }

    @Test
    public void testVerificaDisponibilidadeFalse() {
        // arrange
        int quantidadeDisponivel = 10;
        Produto produto = new Produto.Builder().codigo(2l).descricao("").build();
        Estoque estoque = new Estoque.Builder().produto(produto).quantidadeDisponivel(quantidadeDisponivel).build();
        when(servicoDeEstoque.buscaItemEstoque(2l)).thenReturn(estoque);

        // act
        Boolean result = servicoVendas.verificaDisponibilidade(2l, 11);

        // assert
        assertFalse(result);
    }
}
