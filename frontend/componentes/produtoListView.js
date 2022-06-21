class ProdutoListView {
  _adicionarProduto(produto,i) {
    const produtoView = new ProdutoView(produto);
    const element = produtoView.criarElemento(i);
    this.root.appendChild(element);
    this.views.push(produtoView);
  }

  carregarProdutos(produtos) {
    let i = 0;
    produtos.forEach((x) => {
      this._adicionarProduto(x,i);
      i++;
    });
  }

  constructor(servico, root) {
    this.servico = servico;
    this.root = root;
    this.views = [];
  }
}
