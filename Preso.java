// [HERANÇA] Preso especializa os dados e comportamentos comuns de Pessoa.
public class Preso extends Pessoa {
    private String dataEntrada;
    private String codigoCela;
    private String situacao;

    public Preso(String codigo, String nome, String documento, String telefone,
                 String dataEntrada, String codigoCela, String situacao) {
        super(codigo, nome, documento, telefone);
        setDataEntrada(dataEntrada);
        setCodigoCela(codigoCela);
        setSituacao(situacao);
    }

    public String getDataEntrada() { return dataEntrada; }
    public String getCodigoCela() { return codigoCela; }
    public String getSituacao() { return situacao; }
    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = validarTexto(dataEntrada, "data de entrada");
    }
    public void setCodigoCela(String codigoCela) {
        this.codigoCela = codigoCela == null ? "" : codigoCela.trim();
    }
    public void setSituacao(String situacao) {
        this.situacao = validarTexto(situacao, "situação");
    }
    // [POLIMORFISMO] A listagem chama este método pela referência Pessoa.
    @Override public String getTipo() { return "Preso"; }

    @Override public String exibirDados() {
        return super.exibirDados() + " | Entrada: " + dataEntrada
            + " | Cela: " + (codigoCela.isEmpty() ? "Não definida" : codigoCela)
            + " | Situação: " + situacao;
    }

    public String paraArquivo() {
        return String.join("|", limpar(getCodigo()), limpar(getNome()), limpar(getDocumento()),
            limpar(getTelefone()), limpar(dataEntrada), limpar(codigoCela), limpar(situacao));
    }
}
