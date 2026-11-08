// [HERANÇA] Segunda hierarquia para registros temáticos identificados por código.
public abstract class RegistroPenitenciario {
    private final String codigo;

    protected RegistroPenitenciario(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código não pode ficar vazio.");
        }
        this.codigo = codigo.trim();
    }

    public String getCodigo() { return codigo; }

    public abstract String getTipo();
    public abstract String exibirDados();

    public String identificacao() {
        return getTipo() + " " + codigo;
    }

    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + campo + " não pode ficar vazio.");
        }
        return valor.trim();
    }
}
