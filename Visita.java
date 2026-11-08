public class Visita extends RegistroPenitenciario {
    private String codigoPreso;
    private String nomeVisitante;
    private String documentoVisitante;
    private String data;
    private String horario;

    public Visita(String codigo, String codigoPreso, String nomeVisitante,
                  String documentoVisitante, String data, String horario) {
        super(codigo);
        setCodigoPreso(codigoPreso);
        setNomeVisitante(nomeVisitante);
        setDocumentoVisitante(documentoVisitante);
        setData(data);
        setHorario(horario);
    }

    public String getCodigoPreso() { return codigoPreso; }
    public void setCodigoPreso(String codigoPreso) { this.codigoPreso = validarTexto(codigoPreso, "código do preso"); }
    public void setNomeVisitante(String nomeVisitante) { this.nomeVisitante = validarTexto(nomeVisitante, "nome do visitante"); }
    public void setDocumentoVisitante(String documentoVisitante) {
        this.documentoVisitante = validarTexto(documentoVisitante, "documento do visitante");
    }
    public void setData(String data) { this.data = validarTexto(data, "data"); }
    public void setHorario(String horario) { this.horario = validarTexto(horario, "horário"); }

    @Override public String getTipo() { return "Visita"; }

    @Override public String exibirDados() {
        return identificacao() + " | Preso: " + codigoPreso + " | Visitante: "
            + nomeVisitante + " | Documento: " + documentoVisitante
            + " | Data: " + data + " | Horário: " + horario;
    }
    public String paraArquivo() {
        return String.join("|", Pessoa.limpar(getCodigo()), Pessoa.limpar(codigoPreso),
            Pessoa.limpar(nomeVisitante), Pessoa.limpar(documentoVisitante),
            Pessoa.limpar(data), Pessoa.limpar(horario));
    }

}
