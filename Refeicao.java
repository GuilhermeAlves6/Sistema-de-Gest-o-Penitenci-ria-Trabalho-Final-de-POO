public class Refeicao extends RegistroPenitenciario {
    private String tipoRefeicao;
    private String descricao;
    private String data;
    private String horario;

    public Refeicao(String codigo, String tipo, String descricao, String data, String horario) {
        super(codigo);
        setTipoRefeicao(tipo);
        setDescricao(descricao);
        setData(data);
        setHorario(horario);
    }

    public String getCardapio() { return descricao; }
    public String getData() { return data; }
    public String getHorario() { return horario; }
    public String getTipoRefeicao() { return tipoRefeicao; }
    public void setTipoRefeicao(String tipo) {
        this.tipoRefeicao = validarTexto(tipo, "tipo de refeição");
    }
    public void setDescricao(String descricao) { this.descricao = validarTexto(descricao, "cardápio"); }
    public void setData(String data) { this.data = validarTexto(data, "data"); }
    public void setHorario(String horario) { this.horario = validarTexto(horario, "horário"); }

    @Override public String getTipo() { return "Refeição"; }

    @Override public String exibirDados() {
        return identificacao() + " | Tipo: " + tipoRefeicao + " | Cardápio: " + descricao
            + " | Data: " + data + " | Horário: " + horario;
    }
    public String paraArquivo() {
        return String.join("|", Pessoa.limpar(getCodigo()), Pessoa.limpar(tipoRefeicao),
            Pessoa.limpar(descricao), Pessoa.limpar(data), Pessoa.limpar(horario));
    }
}
