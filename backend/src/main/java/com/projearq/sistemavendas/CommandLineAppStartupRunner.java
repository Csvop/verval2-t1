package com.projearq.sistemavendas;

import com.projearq.sistemavendas.negocio.entidades.Estoque;
import com.projearq.sistemavendas.negocio.entidades.Produto;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeEstoque;
import com.projearq.sistemavendas.negocio.servicos.ServicoDeProduto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final ServicoDeProduto servicoDeProduto;
    private final ServicoDeEstoque servicoDeEstoque;

    @Autowired
    public CommandLineAppStartupRunner(ServicoDeProduto servicoDeProduto, ServicoDeEstoque servicoDeEstoque) {
        this.servicoDeProduto = servicoDeProduto;
        this.servicoDeEstoque = servicoDeEstoque;
    }

    @Override
    public void run(String... args) {

        Produto produto1 = new Produto.Builder()
        .codigo(10L)
        .descricao("Geladeira")
        .precoUnitario(2500.55)
        .build();
        this.servicoDeProduto.adicionaProduto(produto1);

        Estoque estoque1 = new Estoque.Builder()
        .produto(produto1)
        .quantidadeDisponivel(10)
        .build();
        this.servicoDeEstoque.salvarEstoqueProduto(estoque1);

        Produto produto2 = new Produto.Builder()
        .codigo(20L)
        .descricao("Fogao")
        .precoUnitario(1200.00)
        .build();
        this.servicoDeProduto.adicionaProduto(produto2);

        Estoque estoque2 = new Estoque.Builder()
        .produto(produto2)
        .quantidadeDisponivel(0)
        .build();
        this.servicoDeEstoque.salvarEstoqueProduto(estoque2);

        Produto produto3 = new Produto.Builder()
        .codigo(30L)
        .descricao("Lava louça")
        .precoUnitario(4300.00)
        .build();
        this.servicoDeProduto.adicionaProduto(produto3);

        Estoque estoque3 = new Estoque.Builder()
        .produto(produto3)
        .quantidadeDisponivel(7)
        .build();
        this.servicoDeEstoque.salvarEstoqueProduto(estoque3);

        Produto produto4 = new Produto.Builder()
        .codigo(40L)
        .descricao("Lava roupa")
        .precoUnitario(3350.00)
        .build();
        this.servicoDeProduto.adicionaProduto(produto4);

        Estoque estoque4 = new Estoque.Builder()
        .produto(produto4)
        .quantidadeDisponivel(11)
        .build();
        this.servicoDeEstoque.salvarEstoqueProduto(estoque4);

        Produto produto5 = new Produto.Builder()
        .codigo(50L)
        .descricao("Aspirador de pó")
        .precoUnitario(780.00)
        .build();
        this.servicoDeProduto.adicionaProduto(produto5);

        Estoque estoque5 = new Estoque.Builder()
        .produto(produto5)
        .quantidadeDisponivel(22)
        .build();
        this.servicoDeEstoque.salvarEstoqueProduto(estoque5);
    }
}
