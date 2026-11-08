public class Crime extends RegistroPenitenciario {
    private String descricao;
    private String categoria;
    private String codigoPreso;

    public Crime(String codigo, String descricao, String categoria, String codigoPreso) {
        super(codigo);
        setDescricao(descricao);
        setCategoria(categoria);
        setCodigoPreso(codigoPreso);
    }

    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public String getCodigoPreso() { return codigoPreso; }
    public void setDescricao(String descricao) { this.descricao = validarTexto(descricao, "descrição"); }
    public void setCategoria(String categoria) { this.categoria = validarTexto(categoria, "categoria"); }
    public void setCodigoPreso(String codigoPreso) { this.codigoPreso = validarTexto(codigoPreso, "código do preso"); }

    @Override public String getTipo() { return "Crime"; }

    @Override public String exibirDados() {
        return identificacao() + " | " + descricao + " | Categoria: " + categoria
            + " | Código do preso: " + codigoPreso;
    }
    public String paraArquivo() {
        return String.join("|", Pessoa.limpar(getCodigo()), Pessoa.limpar(descricao),
            Pessoa.limpar(categoria), Pessoa.limpar(codigoPreso));
    }
}
