// [ABSTRAÇÃO] Reúne dados comuns; cada tipo concreto define seu tipo e sua exibição.
public abstract class Pessoa {
    private final String codigo;
    private String nome;
    private String documento;
    private String telefone;

    public Pessoa(String codigo, String nome, String documento, String telefone) {
        this.codigo = validarTexto(codigo, "código");
        setNome(nome);
        setDocumento(documento);
        setTelefone(telefone);
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public String getTelefone() { return telefone; }

    // [ENCAPSULAMENTO] Setters validam antes de alterar o estado interno.
    public void setNome(String nome) { this.nome = validarTexto(nome, "nome"); }
    public void setDocumento(String documento) { this.documento = validarTexto(documento, "documento"); }
    public void setTelefone(String telefone) { this.telefone = validarTexto(telefone, "telefone"); }

    public abstract String getTipo();

    public String exibirDados() {
        return getTipo() + " | Código: " + codigo + " | Nome: " + nome
            + " | Documento: " + documento + " | Telefone: " + telefone;
    }

    protected static String limpar(String valor) {
        return valor == null ? "" : valor.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + campo + " não pode ficar vazio.");
        }
        return valor.trim();
    }
}
