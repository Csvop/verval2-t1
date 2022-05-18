class ServicoDeVendas {
  async autoriza(codigo, quantidade) {
    let url = this.baseUrl + "/vendas/autorizacao";
    url += "?codProd=" + codigo + "&qtdade=" + quantidade;

    try {
      let resposta = await fetch(url);
      if (resposta.ok) {
        let aprovacao = await resposta.json();
        return aprovacao;
      } else {
        console.error(resposta.status, resposta.statusText);
        let res = await resposta.json();
        let message = res.message;
        if (message) {
          Swal.fire({
            title: 'Ops!',
            text: res.message,
            icon: 'warning',
            confirmButtonText: 'Ok',
            confirmButtonColor: '#68b0ab'
          });
        }
      }
    } catch (erro) {
      console.error(erro);
    }
    return false;
  }

  async calculaSubtotal(itens) {
    const url = this.baseUrl + "/vendas/subtotal";
    const param = [];

    itens.forEach((item) => {
      param.push({ codigo: item.produto.codigo, quantidade: item.qtdade });
    });

    const otherParam = {
      headers: { "content-type": "application/json" },
      body: JSON.stringify(param),
      method: "POST",
    };

    try {
      let resposta = await fetch(url, otherParam);
      if (resposta.ok) {
        let totais = await resposta.json();
        return totais;
      } else {
        let res = await resposta.json();
        let message = res.message;
        if (message) {
          Swal.fire({
            title: 'Ops!',
            text: res.message,
            icon: 'warning',
            confirmButtonText: 'Ok',
            confirmButtonColor: '#68b0ab'
          });
        }
      }
    } catch (erro) {
      console.log(erro);
    }
    return null;
  }

  async confirmaVenda(itens) {
    const url = this.baseUrl + "/vendas/confirmacao";
    const param = [];

    itens.forEach((item) => {
      param.push({ codigo: item.produto.codigo, quantidade: item.qtdade });
    });

    const otherParam = {
      headers: { "content-type": "application/json" },
      body: JSON.stringify(param),
      method: "POST",
    };

    try {
      let resposta = await fetch(url, otherParam);
      if (resposta.ok) {
        let resp = await resposta.json();
        return resp;
      } else {
        let res = await resposta.json();
        let message = res.message;
        if (message) {
          Swal.fire({
            title: 'Ops!',
            text: res.message,
            icon: 'warning',
            confirmButtonText: 'Ok',
            confirmButtonColor: '#68b0ab'
          });
        }
      }
    } catch (erro) {
      console.log(erro);
    }
    return false;
  }

  async getProdutos() {
    const url = this.baseUrl + "/produtos";
    const produtos = [];

    try {
      let resposta = await fetch(url);
      if (resposta.ok) {
        let dados = await resposta.json();
        console.log(dados);
        for (let i = 0; i < dados.length; i++) {
          produtos.push(
            new Produto(
              dados[i].codigo,
              dados[i].descricao,
              dados[i].preco,
              dados[i].quantidade
            )
          );
        }
      } else {
        let res = await resposta.json();
        let message = res.message;
        if (message) {
          Swal.fire({
            title: 'Ops!',
            text: res.message,
            icon: 'warning',
            confirmButtonText: 'Ok',
            confirmButtonColor: '#68b0ab'
          });
        }
      }
    } catch (erro) {
      console.log(erro);
    }

    return produtos;
  }

  constructor(baseUrl) {
    this.baseUrl = baseUrl;
  }
}
