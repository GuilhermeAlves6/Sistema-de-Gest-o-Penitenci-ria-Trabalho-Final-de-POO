public class Cela {
    private String codigo;
    private String bloco;
    private int capacidade;
    private int ocupacao;

    public Cela(String codigo, String bloco, int capacidade, int ocupacao) {
        this.codigo = Pessoa.validarTexto(codigo, "código da cela");
        setBloco(bloco);
        setCapacidade(capacidade);
        setOcupacao(ocupacao);
    }

    public String getCodigo() { return codigo; }
    public String getBloco() { return bloco; }
    public int getCapacidade() { return capacidade; }
    public int getOcupacao() { return ocupacao; }
    public boolean temVaga() { return ocupacao < capacidade; }
    public void setBloco(String bloco) { this.bloco = Pessoa.validarTexto(bloco, "bloco"); }
    public void setCapacidade(int capacidade) {
        // A ocupação nunca pode superar a capacidade, pois o cadastro depende dessa garantia.
        if (capacidade <= 0 || capacidade < ocupacao) {
            throw new IllegalArgumentException("A capacidade deve ser positiva e não pode ser menor que a ocupação.");
        }
        this.capacidade = capacidade;
    }
    public void setOcupacao(int ocupacao) {
        if (ocupacao < 0 || ocupacao > capacidade) {
            throw new IllegalArgumentException("A ocupação deve estar entre zero e a capacidade.");
        }
        this.ocupacao = ocupacao;
    }
    public void adicionarOcupante() {
        if (!temVaga()) throw new IllegalStateException("A cela está sem vagas.");
        ocupacao++;
    }
    public void removerOcupante() {
        if (ocupacao > 0) ocupacao--;
    }
    public String exibirDados() {
        return "Cela " + codigo + " | Bloco: " + bloco + " | Ocupação: "
            + ocupacao + "/" + capacidade;
    }
    public String paraArquivo() {
        return String.join("|", Pessoa.limpar(codigo), Pessoa.limpar(bloco),
            String.valueOf(capacidade), String.valueOf(ocupacao));
    }
}
