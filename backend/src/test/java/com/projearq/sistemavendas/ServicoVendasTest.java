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

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    private static final String driverDoChrome = "D:\\Documentos\\VERVAL\\chromedriver.exe";

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
        // arrange
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
        // arrange
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
        // arrange
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

    @Test
    @DisplayName("Verifica se eu consigo usar o selenium aqui")
    public void testaSelenium() {
        final boolean fecharBrowserNoFinal = false;
        boolean esteTestePassou = false;
        System.out.println("+++++ Iniciando Exemplo 1...");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        try {
            // Usa a calculadora do Google
            driver.get("https://google.com");
            WebElement caixaDeBusca = driver.findElement(By.name("q"));
            caixaDeBusca.sendKeys("20 + 22");
            caixaDeBusca.sendKeys(Keys.ENTER);

            WebDriverWait wait1 = new WebDriverWait(driver, 10);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("cwos")));

            WebElement elementoResultado = driver.findElement(By.id("cwos"));
            String strResult = elementoResultado.getText();
            int iResult = Integer.parseInt(strResult);

            esteTestePassou = (iResult == 45);

        } catch (Exception e) {
            esteTestePassou = false;
            System.err.println(e.toString());
        } finally {
            if (fecharBrowserNoFinal) {
                driver.quit();
            }
        }

        System.out.println(">>>>>>>>>>>>>>> Resultado do teste: " + ((esteTestePassou) ? "PASSOU" : "FALHOU"));
        System.out.println("+++++++++++++++ Fim do exemplo 1.");
    }

    @Test
    @DisplayName("Tentando abrir o projeto")
    public void testaCompra() {
        System.out.println("+++++ Iniciando Exemplo 1...");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///D:/Documentos/VERVAL/VerVal2/SistemaDeVendas/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        WebElement produtoClickado = driver.findElement(By.id("select0"));
        produtoClickado.click();

        WebElement elementoResultado = driver.findElement(By.id("txtTotal"));
        String strResult = elementoResultado.getText();

        assertEquals(strResult, "Total: R$ 2800.62");

        driver.quit();
    }
}
