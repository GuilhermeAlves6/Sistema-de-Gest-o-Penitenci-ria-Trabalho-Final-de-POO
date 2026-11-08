// [HERANÇA] PolicialPenal é a segunda especialização concreta de Pessoa.
public class PolicialPenal extends Pessoa {
    private String matricula;
    private String turno;

    public PolicialPenal(String codigo, String nome, String documento, String telefone,
                         String matricula, String turno) {
        super(codigo, nome, documento, telefone);
        setMatricula(matricula);
        setTurno(turno);
    }

    public String getMatricula() { return matricula; }
    public String getTurno() { return turno; }
    public void setMatricula(String matricula) {
        this.matricula = validarTexto(matricula, "matrícula");
    }
    public void setTurno(String turno) {
        this.turno = validarTexto(turno, "turno");
    }
    // [POLIMORFISMO] A mesma operação exibe o tipo específico do policial.
    @Override public String getTipo() { return "Policial Penal"; }

    @Override public String exibirDados() {
        return super.exibirDados() + " | Matrícula: " + matricula + " | Turno: " + turno;
    }

    public String paraArquivo() {
        return String.join("|", limpar(getCodigo()), limpar(getNome()), limpar(getDocumento()),
            limpar(getTelefone()), limpar(matricula), limpar(turno));
    }
}
