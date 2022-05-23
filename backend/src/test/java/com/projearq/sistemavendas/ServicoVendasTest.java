package com.projearq.sistemavendas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.projearq.sistemavendas.adaptadores.repositorios.VendasRepository;
import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.aplicacao.servicos.restricoes.RestricoesNivelBaixo;
import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeEstoque;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeProduto;
import com.projearq.sistemavendas.negocio.servicos.ServicoVendas;
import com.projearq.sistemavendas.negocio.strategy.IRestricoesStrategy;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServicoVendasTest {
    
    @InjectMocks
    private ServicoVendas servicoVendas;

    @Mock
    private ServicoDeEstoque servicoDeEstoque;

    @Mock
    private VendasRepository vendasRepository;

    @Mock
    private ServicoDeProduto servicoDeProduto;

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

    @Test
    @DisplayName("Verifica se os calculos de subtotal estão corretos para produtos sem restrição de quantidade")
    public void testecalCulaSubtotal() {
        //arrange
        ServicoVendas servicoVendas = new ServicoVendas(vendasRepository, servicoDeProduto, servicoDeEstoque);
        IRestricoesStrategy restricoes = new RestricoesNivelBaixo();

        List<ProdutoDTO> produtoDtoListMock = new ArrayList<>();
        produtoDtoListMock.add(new ProdutoDTO(1l, "", 100.0, 1));

        // act
        double result = servicoVendas.calculaSubtotal(new ArrayList<ProdutoDTO>(), restricoes, 100.0);

        // assert
        assertEquals(result, 100.0);
    }

    @Test
    @DisplayName("Verifica se os calculos de subtotal estão corretos para produtos com restrição de quantidade")
    public void testecalCulaSubtotalComRestricao() {
        //arrange
        ServicoVendas servicoVendas = new ServicoVendas(vendasRepository, servicoDeProduto, servicoDeEstoque);
        IRestricoesStrategy restricoes = new RestricoesNivelBaixo();

        List<ProdutoDTO> produtoDtoListMock = new ArrayList<>();
        produtoDtoListMock.add(new ProdutoDTO(1l, "", 300.0, 1));

        // act
        double result = servicoVendas.calculaSubtotal(new ArrayList<ProdutoDTO>(), restricoes, 200.0);

        // assert
        assertEquals(result, 200.0);
    }

    @Test
    @DisplayName("Verifica se os calculos de subtotal estão corretos para multiplos produtos")
    public void testecalCulaSubtotals() {
        //arrange
        ServicoVendas servicoVendas = new ServicoVendas(vendasRepository, servicoDeProduto, servicoDeEstoque);
        IRestricoesStrategy restricoes = new RestricoesNivelBaixo();

        List<ProdutoDTO> produtoDtoListMock = new ArrayList<>();
        produtoDtoListMock.add(new ProdutoDTO(1l, "", 100.0, 1));
        produtoDtoListMock.add(new ProdutoDTO(2l, "", 200.0, 1));
        produtoDtoListMock.add(new ProdutoDTO(3l, "", 300.0, 1));

        // act
        double result = servicoVendas.calculaSubtotal(new ArrayList<ProdutoDTO>(), restricoes, 600.0);

        // assert
        assertEquals(result, 600.0);
    }
}
