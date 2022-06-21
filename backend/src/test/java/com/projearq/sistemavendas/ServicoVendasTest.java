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

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.size;

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

    private static final String driverDoChrome = "C:\\Users\\gg_ve\\OneDrive\\Documentos\\PUCRS\\dev\\chromedriver.exe";

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


/*
 * 
 * INICIANDO TESTES COM SELENIUM
 * 
 */

    @Test
    @DisplayName("Testa valor total de Produto + Imposto")
    public void testaValorTabelado() {
        System.out.println("Iniciando Exemplo 1");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        // Espera item "Geladeira"
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona item "Geladeira"
        WebElement produtoClickado = driver.findElement(By.id("select0"));
        produtoClickado.click();

        // Seleciona valor total do carrinho
        WebElement elementoResultado = driver.findElement(By.id("txtTotal"));
        String strResult = elementoResultado.getText();

        // Compara com valor tabelado
        assertEquals(strResult, "Total: R$ 2800.62");

        driver.quit();
    }

    @Test
    @DisplayName("Testa botão de limpar o carrinho")
    public void testaLixeira() {
        System.out.println("Iniciando Exemplo 2");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona itens "Geladeira" e "Lava louça"
        WebElement produtoClickado = driver.findElement(By.id("select0"));
        produtoClickado.click();
        WebElement produtoClickado1 = driver.findElement(By.id("select2"));
        produtoClickado1.click();

        // Seleciona lixeira e limpa o carrinho
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnClear")));
        WebElement lixeira = driver.findElement(By.id("btnClear"));
        lixeira.click();

        // Seleciona valor total do carrinho
        WebElement elementoResultado = driver.findElement(By.id("txtTotal"));
        String strResult = elementoResultado.getText();

        // Verifica se o carrinho ficou vazio 
        assertEquals(strResult, "Total: R$ 0.00");

        driver.quit();

    }

    @Test
    @DisplayName("Testa compra de produto indisponivel")
    public void testaIndisponivel() {
        System.out.println("Iniciando Exemplo 3");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona itens "Fogao" que está indisponível
        WebElement produtoClickado = driver.findElement(By.id("select1"));
        produtoClickado.click();

        // Recebe mensagem de erro
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
        WebElement msgErro = driver.findElement(By.id("swal2-title"));

        assertEquals(msgErro.getText(), "Ops!");

        driver.quit();
    }

    @Test
    @DisplayName("Testa exceção de valor limite")
    public void testaValorLimite() {
        System.out.println("Iniciando Exemplo 4");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona iten "Geladeira" e coloca no carrinho até estourar o valor limite
        WebElement produtoClickado = driver.findElement(By.id("select0"));
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();

        // Recebe mensagem de erro
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
        WebElement msgErro = driver.findElement(By.id("swal2-title"));

        assertEquals(msgErro.getText(), "Ops!");

        driver.quit();
    }

    @Test
    @DisplayName("Testa compra simples")
    public void testaCompra() {
        System.out.println("Iniciando Exemplo 5");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona iten "Geladeira" e finaliza a compra
        WebElement produtoClickado = driver.findElement(By.id("select0"));
        produtoClickado.click();
        WebElement finalizaCompra = driver.findElement(By.id("btnCheckout"));
        finalizaCompra.click();

        // Recebe de sucesso
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
        WebElement msgSucesso = driver.findElement(By.id("swal2-title"));

        assertEquals(msgSucesso.getText(), "Sucesso!");

        driver.quit();
    }

    @Test
    @DisplayName("Testa exceção de quantidade de um produto")
    public void testaQuantidadeLimite() {
        System.out.println("Iniciando Exemplo 6");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona iten "Aspirador de pó" e coloca no carrinho até estourar a quantidade limite
        WebElement produtoClickado = driver.findElement(By.id("select4"));
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();
        produtoClickado.click();

        // Recebe mensagem de erro
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
        WebElement msgErro = driver.findElement(By.id("swal2-title"));

        assertEquals(msgErro.getText(), "Ops!");

        driver.quit();
    }

    @Test
    @DisplayName("Testa compra de diversos itens")
    public void testaCompraConjunta() {
        System.out.println("Iniciando Exemplo 7");
        // Propriedades Gerais
        System.setProperty("webdriver.chrome.driver", driverDoChrome);

        // Configurações do Chrome
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Instancia o WebDriver do Chrome
        final WebDriver driver = new ChromeDriver();

        // Abre o index do projeto
        driver.get("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("select0")));

        // Seleciona os itens "Geladeira", "Lava louça" e "Aspirador de pó" e finaliza a compra
        WebElement produtoClickado = driver.findElement(By.id("select0"));
        produtoClickado.click();
        WebElement produtoClickado1 = driver.findElement(By.id("select2"));
        produtoClickado1.click();
        WebElement produtoClickado2 = driver.findElement(By.id("select4"));
        produtoClickado2.click();
        WebElement finalizaCompra = driver.findElement(By.id("btnCheckout"));
        finalizaCompra.click();

        // Recebe de sucesso
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));
        WebElement msgSucesso = driver.findElement(By.id("swal2-title"));

        assertEquals(msgSucesso.getText(), "Sucesso!");

        driver.quit();
    }

/*
 * 
 * FINALIZANDO TESTES COM SELENIUM
 * 
 *  INICIANDO TESTES COM SELENIDE
 * 
 */

    @Test
    @DisplayName("Testa Valor Tabelado Selenide")
    public void testaValorSelenide() {
        System.out.println("Iniciando Exemplo 1 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select0")).click();

        $(By.id("txtTotal")).shouldHave(text("Total: R$ 2800.62"));
    }

    @Test
    @DisplayName("Testa Lixeira Selenide")
    public void testaLixeiraSelenide() {
        System.out.println("Iniciando Exemplo 2 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select0")).click();
        $(By.id("select2")).click();

        $(By.id("btnClear")).click();

        $(By.id("txtTotal")).shouldHave(text("Total: R$ 0.00"));
    }

    @Test
    @DisplayName("Testa Item Indisponível Selenide")
    public void testaIndisponivelSelenide() {
        System.out.println("Iniciando Exemplo 3 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select1")).click();
        
        $(By.id("swal2-title")).shouldHave(text("Ops!"));
    }

    @Test
    @DisplayName("Testa Valor Limite Selenide")
    public void testaValorLimiteSelenide() {
        System.out.println("Iniciando Exemplo 4 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select0")).click();
        $(By.id("select0")).click();
        $(By.id("select0")).click();
        $(By.id("select0")).click();
        
        $(By.id("swal2-title")).shouldHave(text("Ops!"));
    }

    @Test
    @DisplayName("Testa Compra Simples Selenide")
    public void testaCompraSelenide() {
        System.out.println("Iniciando Exemplo 5 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select0")).click();
        $(By.id("btnCheckout")).click();
        
        $(By.id("swal2-title")).shouldHave(text("Sucesso!"));
    }

    @Test
    @DisplayName("Testa Quantidade Limite Selenide")
    public void testaQtdLimiteSelenide() {
        System.out.println("Iniciando Exemplo 6 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        $(By.id("select4")).click();
        
        
        $(By.id("swal2-title")).shouldHave(text("Ops!"));
    }

    @Test
    @DisplayName("Testa Compra Simples Selenide")
    public void testaCompraConjuntaSelenide() {
        System.out.println("Iniciando Exemplo 5 Selenide");
        // Propriedades Gerais
        open("file:///C:/Users/gg_ve/OneDrive/Documentos/PUCRS/VV2/verval2-t1/frontend/index.html");

        $(By.id("select0")).click();
        $(By.id("select2")).click();
        $(By.id("select4")).click();
        $(By.id("btnCheckout")).click();
        
        $(By.id("swal2-title")).shouldHave(text("Sucesso!"));
    }
}
