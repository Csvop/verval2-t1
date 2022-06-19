package com.projearq.sistemavendas;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServicoVendasSeleniumTest{

    private static final String driverDoChrome = "C:\\Users\\gg_ve\\OneDrive\\Documentos\\PUCRS\\dev\\chromedriver.exe";

    public static void exemploQualquer(){
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

        }
        catch (Exception e) {
            esteTestePassou = false;
            System.err.println(e.toString());
        } finally {
            if (fecharBrowserNoFinal) {
                driver.quit();
            }
        }

        System.out.println(">>>>>>>>>>>>>>> Resultado do teste: " + ((esteTestePassou)? "PASSOU" : "FALHOU"));
        System.out.println("+++++++++++++++ Fim do exemplo 1.");
    }
}