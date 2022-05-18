class SaidaView {
  icError = "&#x26D4"; // ⛔
  icOk = "&#x2705"; // ✅

  erroInternoDoServidor() {
    this.root.innerHTML = this.icError + " Erro Interno do Servidor";
  }

  quantidadeIndisponivel() {
    Swal.fire({
      title: 'Ops!',
      text: 'Quantidade excede o dispon\u00EDel para o produto!',
      icon: 'warning',
      confirmButtonText: 'Ok',
      confirmButtonColor: '#68b0ab'
    });

    this.root.innerHTML = this.icError + " Quantidade excede o dispon&iacute;vel para o produto";
  }

  produtoIndisponivel() {
    Swal.fire({
      title: 'Ops!',
      text: 'Produto Indispon\u00EDvel!',
      icon: 'warning',
      confirmButtonText: 'Ok',
      confirmButtonColor: '#68b0ab'
    });

    this.root.innerHTML = this.icError + " Produto Indispon&iacute;vel";
  }

  vendaSucesso() {
    Swal.fire({
      title: 'Sucesso!',
      text: 'Venda conclu\u00EDda com sucesso!',
      icon: 'success',
      confirmButtonText: 'Ok',
      confirmButtonColor: '#68b0ab'
    });

    this.root.innerHTML = this.icOk + " Venda conclu&iacute;da com sucesso";
  }

  limpar() {
    this.root.innerHTML = "N&atilde;o h&aacute; nenhuma sa&iacute;da";
  }

  constructor(root) {
    this.root = root;
    this.limpar();
  }
}
