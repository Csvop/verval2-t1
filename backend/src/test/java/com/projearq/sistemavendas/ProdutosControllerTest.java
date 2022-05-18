package com.projearq.sistemavendas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.projearq.sistemavendas.adaptadores.controllers.ProdutosController;
import com.projearq.sistemavendas.aplicacao.casosDeUso.AdicionaProdutoUC;
import com.projearq.sistemavendas.aplicacao.casosDeUso.ConsultaProdutosUC;
import com.projearq.sistemavendas.aplicacao.dtos.ProdutoDTO;
import com.projearq.sistemavendas.negocio.entidades.Produto;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProdutosControllerTest {

    @InjectMocks
    private ProdutosController produtosController;

    @Mock
    private ConsultaProdutosUC consultaProdutosUC;

    @Mock
    private AdicionaProdutoUC adicionaProdutoUC;

    @Test
	public void testProdutosControllerGet() {
        List<ProdutoDTO> prodList = new ArrayList<ProdutoDTO>();
        ProdutoDTO produtoDTO = new ProdutoDTO(1l, "", 2.0, 3);
        prodList.add(produtoDTO);
        when(consultaProdutosUC.run()).thenReturn(prodList);

		List<ProdutoDTO> result = produtosController.consultaProdutos();
		assertEquals(prodList, result);
	}

    @Test
    public void testProdutosControllerPost() {
        Produto produto = new Produto.Builder().build();
        when(adicionaProdutoUC.run(produto)).thenReturn(produto);

        Produto result = produtosController.adicionaProduto(produto);

        assertEquals(produto, result);
    }
    
}
