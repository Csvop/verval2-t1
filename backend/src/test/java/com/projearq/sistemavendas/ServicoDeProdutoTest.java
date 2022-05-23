package com.projearq.sistemavendas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.repositorios.IProdutosRepository;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeEstoque;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeProduto;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServicoDeProdutoTest {
    @Mock
    private IProdutosRepository produtosRepository;
    
    @Mock
    private ServicoDeEstoque servicoDeEstoque;
    
    @Test
    public void testeConsultaProdutosDeveRetornarUmaListaDeProdutos() {
        //arrange
        ServicoDeProduto servicoDeProduto = new ServicoDeProduto(produtosRepository, servicoDeEstoque);

        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(new Produto.Builder().codigo(1l).descricao("1").build());
        produtos.add(new Produto.Builder().codigo(2l).descricao("2").build());
        produtos.add(new Produto.Builder().codigo(3l).descricao("3").build());

        Mockito.when(produtosRepository.consultaProdutos()).thenReturn(produtos);

        Mockito.when(servicoDeEstoque.buscaItemEstoque(1l)).thenReturn(new Estoque.Builder().produto(new Produto.Builder().codigo(1l).descricao("1").build()).quantidadeDisponivel(10).build());
        Mockito.when(servicoDeEstoque.buscaItemEstoque(2l)).thenReturn(new Estoque.Builder().produto(new Produto.Builder().codigo(2l).descricao("2").build()).quantidadeDisponivel(10).build());
        Mockito.when(servicoDeEstoque.buscaItemEstoque(3l)).thenReturn(new Estoque.Builder().produto(new Produto.Builder().codigo(3l).descricao("3").build()).quantidadeDisponivel(10).build());

        // act
        List<ProdutoDTO> result = servicoDeProduto.consultaProdutos();

        // assert
        for (int i = 0; i < produtos.size(); i++) {
            assertEquals(result.get(i).getCodigo(), produtos.get(i).getCodigo());
            assertEquals(result.get(i).getDescricao(), produtos.get(i).getDescricao());
        }
    }
}