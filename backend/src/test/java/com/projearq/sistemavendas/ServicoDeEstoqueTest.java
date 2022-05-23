package com.projearq.sistemavendas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeEstoque;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServicoDeEstoqueTest {

    @Mock
    private ServicoDeEstoque servicoDeEstoque;
    
    @Test
    public void testDiminuiQuantidade() {
        // arrange
        int quantidadeDisponivel = 10;
        Produto produto = new Produto.Builder().codigo(1l).descricao("").build();
        Estoque estoque = new Estoque.Builder().produto(produto).quantidadeDisponivel(quantidadeDisponivel).build();
        when(servicoDeEstoque.buscaItemEstoque(1l)).thenReturn(estoque);
        when(servicoDeEstoque.salvarEstoqueProduto(estoque)).thenReturn(estoque);

        int expectedQuantidadeDisponivel = 8;
        Produto expectedProduto = new Produto.Builder().codigo(1l).descricao("").build();
        Estoque expectedEstoque = new Estoque.Builder().produto(expectedProduto).quantidadeDisponivel(expectedQuantidadeDisponivel).build();

        // act
        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() - 2);
        servicoDeEstoque.diminuiQuantidadeItemEstoque(1l, 2);

        // assert
        assertEquals(expectedEstoque.getQuantidadeDisponivel(), estoque.getQuantidadeDisponivel());
    }

    @Test
    public void testDiminuiQuantidadeZero() {
        // arrange
        int quantidadeDisponivel = 10;
        Produto produto = new Produto.Builder().codigo(2l).descricao("").build();
        Estoque estoque = new Estoque.Builder().produto(produto).quantidadeDisponivel(quantidadeDisponivel).build();
        when(servicoDeEstoque.buscaItemEstoque(2l)).thenReturn(estoque);
        when(servicoDeEstoque.salvarEstoqueProduto(estoque)).thenReturn(estoque);

        int expectedQuantidadeDisponivel = 10;
        Produto expectedProduto = new Produto.Builder().codigo(2l).descricao("").build();
        Estoque expectedEstoque = new Estoque.Builder().produto(expectedProduto).quantidadeDisponivel(expectedQuantidadeDisponivel).build();

        // act
        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() - 0);
        servicoDeEstoque.diminuiQuantidadeItemEstoque(2l, 0);

        // assert
        assertEquals(expectedEstoque.getQuantidadeDisponivel(), estoque.getQuantidadeDisponivel());
    }


    @Test
    public void TestDiminuiQuantidadeNegativo(){
        int quantidadeDisponivel = 9;
        Produto produto = new Produto.Builder().codigo(3l).descricao("").build();
        Estoque estoque = new Estoque.Builder().produto(produto).quantidadeDisponivel(quantidadeDisponivel).build();
        when(servicoDeEstoque.buscaItemEstoque(3l)).thenReturn(estoque);
        when(servicoDeEstoque.salvarEstoqueProduto(estoque)).thenReturn(estoque);

        int expectedQuantidadeDisponivel = -1;
        Produto expectedProduto = new Produto.Builder().codigo(3l).descricao("").build();
        Estoque expectedEstoque = new Estoque.Builder().produto(expectedProduto).quantidadeDisponivel(expectedQuantidadeDisponivel).build();

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() - 10);
        servicoDeEstoque.diminuiQuantidadeItemEstoque(3l, 10);

        assertEquals(expectedEstoque.getQuantidadeDisponivel(), estoque.getQuantidadeDisponivel());
    }

}
