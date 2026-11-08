import java.util.ArrayList;
import java.util.List;

public class Penitenciaria {
    private final List<Preso> presos = new ArrayList<>();
    private final List<PolicialPenal> policiais = new ArrayList<>();
    private final List<Cela> celas = new ArrayList<>();
    private final List<Crime> crimes = new ArrayList<>();
    private final List<Visita> visitas = new ArrayList<>();
    private final List<Refeicao> refeicoes = new ArrayList<>();
    private final Armazenamento armazenamento = new Armazenamento();

    public Penitenciaria() { carregarDados(); }

    public List<Preso> getPresos() { return presos; }
    public List<PolicialPenal> getPoliciais() { return policiais; }
    public List<Cela> getCelas() { return celas; }
    public List<Crime> getCrimes() { return crimes; }
    public List<Visita> getVisitas() { return visitas; }
    public List<Refeicao> getRefeicoes() { return refeicoes; }

    public boolean existePreso(String codigo) {
        return presos.stream().anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }
    public boolean existePolicial(String codigo) {
        return policiais.stream().anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }
    public boolean existeCela(String codigo) {
        return celas.stream().anyMatch(c -> c.getCodigo().equalsIgnoreCase(codigo));
    }
    public boolean existeCrime(String codigo) {
        return crimes.stream().anyMatch(c -> c.getCodigo().equalsIgnoreCase(codigo));
    }
    public boolean existeVisita(String codigo) {
        return visitas.stream().anyMatch(v -> v.getCodigo().equalsIgnoreCase(codigo));
    }
    public boolean existeRefeicao(String codigo) {
        return refeicoes.stream().anyMatch(r -> r.getCodigo().equalsIgnoreCase(codigo));
    }

    public void cadastrarCela(Cela cela) {
        celas.add(cela);
        salvarCelas();
    }

    public boolean cadastrarPreso(Preso preso) {
        if (existePreso(preso.getCodigo())) return false;
        if (!preso.getCodigoCela().isEmpty()) {
            Cela cela = buscarCela(preso.getCodigoCela());
            if (cela == null || !cela.temVaga()) return false;
            cela.adicionarOcupante();
            salvarCelas();
        }
        presos.add(preso);
        salvarPresos();
        return true;
    }

    public boolean cadastrarPolicial(PolicialPenal policial) {
        if (existePolicial(policial.getCodigo())) return false;
        policiais.add(policial);
        salvarPoliciais();
        return true;
    }

    public boolean cadastrarCrime(Crime crime) {
        if (existeCrime(crime.getCodigo()) || !existePreso(crime.getCodigoPreso())) return false;
        crimes.add(crime);
        salvarCrimes();
        return true;
    }

    public boolean cadastrarVisita(Visita visita) {
        if (existeVisita(visita.getCodigo()) || !existePreso(visita.getCodigoPreso())) return false;
        visitas.add(visita);
        salvarVisitas();
        return true;
    }

    public boolean cadastrarRefeicao(Refeicao refeicao) {
        if (existeRefeicao(refeicao.getCodigo())) return false;
        refeicoes.add(refeicao);
        salvarRefeicoes();
        return true;
    }

    public Cela buscarCela(String codigo) {
        return celas.stream().filter(c -> c.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
    }

    public Preso buscarPreso(String codigo) {
        return presos.stream().filter(p -> p.getCodigo().equalsIgnoreCase(codigo)).findFirst().orElse(null);
    }

    public boolean editarPreso(String codigo, Preso atualizado) {
        Preso atual = buscarPreso(codigo);
        if (atual == null) return false;

        Cela celaAtual = atual.getCodigoCela().isEmpty() ? null : buscarCela(atual.getCodigoCela());
        Cela novaCela = atualizado.getCodigoCela().isEmpty() ? null : buscarCela(atualizado.getCodigoCela());
        if (!atualizado.getCodigoCela().isEmpty() && novaCela == null) return false;
        // Valida a nova alocação antes de liberar a cela atual para não perder a vaga em uma edição inválida.
        if (novaCela != null && novaCela != celaAtual && !novaCela.temVaga()) return false;

        if (novaCela != celaAtual) {
            if (novaCela != null) novaCela.adicionarOcupante();
            if (celaAtual != null) celaAtual.removerOcupante();
        }
        presos.set(presos.indexOf(atual), atualizado);
        salvarTudo();
        return true;
    }

    public boolean removerPreso(String codigo) {
        Preso p = buscarPreso(codigo);
        if (p == null) return false;
        presos.remove(p);
        if (!p.getCodigoCela().isEmpty()) {
            Cela cela = buscarCela(p.getCodigoCela());
            if (cela != null) cela.removerOcupante();
        }
        crimes.removeIf(c -> c.getCodigoPreso().equalsIgnoreCase(codigo));
        visitas.removeIf(v -> v.getCodigoPreso().equalsIgnoreCase(codigo));
        salvarTudo();
        return true;
    }

    public void listarPessoas() {
        if (presos.isEmpty() && policiais.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }
        // [POLIMORFISMO] Preso e PolicialPenal são exibidos via referência da superclasse Pessoa.
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.addAll(presos);
        pessoas.addAll(policiais);
        for (Pessoa pessoa : pessoas) System.out.println(pessoa.exibirDados());
    }

    public void listarRegistrosTematicos() {
        List<RegistroPenitenciario> registros = new ArrayList<>();
        registros.addAll(crimes);
        registros.addAll(visitas);
        registros.addAll(refeicoes);
        if (registros.isEmpty()) {
            System.out.println("Nenhum registro temático cadastrado.");
            return;
        }
        // A superclasse comum permite listar entidades distintas sem ramificações por tipo.
        for (RegistroPenitenciario registro : registros) System.out.println(registro.exibirDados());
    }

    public void salvarTudo() {
        salvarPresos(); salvarPoliciais(); salvarCelas(); salvarCrimes(); salvarVisitas(); salvarRefeicoes();
    }

    private void salvarPresos() {
        List<String> linhas = new ArrayList<>();
        for (Preso p : presos) linhas.add(p.paraArquivo());
        armazenamento.salvar("presos.txt", linhas);
    }
    private void salvarPoliciais() {
        List<String> linhas = new ArrayList<>();
        for (PolicialPenal p : policiais) linhas.add(p.paraArquivo());
        armazenamento.salvar("policiais.txt", linhas);
    }
    private void salvarCelas() {
        List<String> linhas = new ArrayList<>();
        for (Cela c : celas) linhas.add(c.paraArquivo());
        armazenamento.salvar("celas.txt", linhas);
    }
    private void salvarCrimes() {
        List<String> linhas = new ArrayList<>();
        for (Crime c : crimes) linhas.add(c.paraArquivo());
        armazenamento.salvar("crimes.txt", linhas);
    }
    private void salvarVisitas() {
        List<String> linhas = new ArrayList<>();
        for (Visita v : visitas) linhas.add(v.paraArquivo());
        armazenamento.salvar("visitas.txt", linhas);
    }
    private void salvarRefeicoes() {
        List<String> linhas = new ArrayList<>();
        for (Refeicao r : refeicoes) linhas.add(r.paraArquivo());
        armazenamento.salvar("refeicoes.txt", linhas);
    }

    private void carregarDados() {
        for (String[] d : armazenamento.ler("celas.txt")) {
            try { if (d.length >= 4) celas.add(new Cela(d[0], d[1], Integer.parseInt(d[2]), Integer.parseInt(d[3]))); }
            catch (RuntimeException e) { System.out.println("Registro de cela ignorado."); }
        }
        for (String[] d : armazenamento.ler("presos.txt")) {
            try { if (d.length >= 7) presos.add(new Preso(d[0],d[1],d[2],d[3],d[4],d[5],d[6])); }
            catch (RuntimeException e) { System.out.println("Registro de preso ignorado."); }
        }
        for (String[] d : armazenamento.ler("policiais.txt")) {
            try { if (d.length >= 6) policiais.add(new PolicialPenal(d[0],d[1],d[2],d[3],d[4],d[5])); }
            catch (RuntimeException e) { System.out.println("Registro de policial ignorado."); }
        }
        for (String[] d : armazenamento.ler("crimes.txt")) {
            try { if (d.length >= 4) crimes.add(new Crime(d[0],d[1],d[2],d[3])); }
            catch (RuntimeException e) { System.out.println("Registro de crime ignorado."); }
        }
        for (String[] d : armazenamento.ler("visitas.txt")) {
            try { if (d.length >= 6) visitas.add(new Visita(d[0],d[1],d[2],d[3],d[4],d[5])); }
            catch (RuntimeException e) { System.out.println("Registro de visita ignorado."); }
        }
        for (String[] d : armazenamento.ler("refeicoes.txt")) {
            try { if (d.length >= 5) refeicoes.add(new Refeicao(d[0],d[1],d[2],d[3],d[4])); }
            catch (RuntimeException e) { System.out.println("Registro de refeição ignorado."); }
        }
    }
}
