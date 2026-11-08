import java.util.Scanner;

public class Principal {
    private static final Scanner sc = new Scanner(System.in);
    private static final Penitenciaria sistema = new Penitenciaria();

    // [MENU] Todas as operações do sistema são acessadas por este loop.
    public static void main(String[] args) {
        int opcao;
        do {
            menu();
            opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1: cadastrarCela(); break;
                case 2: cadastrarPreso(); break;
                case 3: cadastrarPolicial(); break;
                case 4: cadastrarCrime(); break;
                case 5: cadastrarVisita(); break;
                case 6: cadastrarRefeicao(); break;
                case 7: listarPessoas(); break;
                case 8: listarCelas(); break;
                case 9: listarCrimes(); break;
                case 10: listarVisitas(); break;
                case 11: listarRefeicoes(); break;
                case 12: buscarPreso(); break;
                case 13: removerPreso(); break;
                case 14: editarPreso(); break;
                case 15: relatorioOcupacao(); break;
                case 16: listarRegistrosTematicos(); break;
                case 0:
                    sistema.salvarTudo();
                    System.out.println("Dados salvos. Sistema encerrado.");
                    break;
                default: System.out.println("Opção inválida.");
            }
            if (opcao != 0) pausar();
        } while (opcao != 0);
        sc.close();
    }

    private static void menu() {
        System.out.println("\n========== SISTEMA DE GESTÃO PENITENCIÁRIA ==========");
        System.out.println("1  - Cadastrar cela");
        System.out.println("2  - Cadastrar preso");
        System.out.println("3  - Cadastrar policial penal");
        System.out.println("4  - Registrar crime associado a preso");
        System.out.println("5  - Agendar visita");
        System.out.println("6  - Cadastrar refeição/cardápio");
        System.out.println("7  - Listar presos e policiais");
        System.out.println("8  - Listar celas");
        System.out.println("9  - Listar crimes");
        System.out.println("10 - Listar visitas");
        System.out.println("11 - Listar refeições");
        System.out.println("12 - Buscar preso por código");
        System.out.println("13 - Excluir preso");
        System.out.println("14 - Editar preso");
        System.out.println("15 - Relatório de ocupação das celas");
        System.out.println("16 - Listar todos os registros temáticos");
        System.out.println("0  - Salvar e sair");
    }

    private static void cadastrarCela() {
        String codigo = lerTextoObrigatorio("Código da cela: ");
        if (sistema.existeCela(codigo)) { System.out.println("Já existe uma cela com esse código."); return; }
        String bloco = lerTextoObrigatorio("Bloco: ");
        int capacidade = lerInt("Capacidade máxima: ");
        if (capacidade <= 0) { System.out.println("A capacidade deve ser maior que zero."); return; }
        sistema.cadastrarCela(new Cela(codigo, bloco, capacidade, 0));
        System.out.println("Cela cadastrada.");
    }

    private static void cadastrarPreso() {
        String codigo = lerTextoObrigatorio("Código do preso: ");
        String nome = lerTextoObrigatorio("Nome completo: ");
        String documento = lerTextoObrigatorio("Documento: ");
        String telefone = lerTextoObrigatorio("Telefone: ");
        String entrada = lerTextoObrigatorio("Data de entrada (dd/mm/aaaa): ");
        String codigoCela = lerTexto("Código da cela (deixe vazio se ainda não houver cela): ");
        if (!codigoCela.isEmpty() && !sistema.existeCela(codigoCela)) {
            System.out.println("Essa cela não existe. Cadastre a cela primeiro."); return;
        }
        String situacao = lerTextoObrigatorio("Situação processual (ex.: provisório/condenado): ");
        try {
            Preso preso = new Preso(codigo, nome, documento, telefone, entrada, codigoCela, situacao);
            if (sistema.cadastrarPreso(preso)) System.out.println("Preso cadastrado.");
            else System.out.println("Não foi possível cadastrar: código repetido ou cela sem vagas.");
        } catch (IllegalArgumentException e) {
            System.out.println("Dados inválidos: " + e.getMessage());
        }
    }

    private static void cadastrarPolicial() {
        String codigo = lerTextoObrigatorio("Código do policial: ");
        String nome = lerTextoObrigatorio("Nome completo: ");
        String documento = lerTextoObrigatorio("Documento: ");
        String telefone = lerTextoObrigatorio("Telefone: ");
        String matricula = lerTextoObrigatorio("Matrícula funcional: ");
        String turno = lerTextoObrigatorio("Turno (dia/noite): ");
        try {
            if (sistema.cadastrarPolicial(new PolicialPenal(codigo,nome,documento,telefone,matricula,turno)))
                System.out.println("Policial cadastrado.");
            else System.out.println("Já existe um policial com esse código.");
        } catch (IllegalArgumentException e) {
            System.out.println("Dados inválidos: " + e.getMessage());
        }
    }

    private static void cadastrarCrime() {
        String codigo = lerTextoObrigatorio("Código do registro: ");
        String descricao = lerTextoObrigatorio("Descrição do crime: ");
        String categoria = lerTextoObrigatorio("Categoria/observação: ");
        String codigoPreso = lerTextoObrigatorio("Código do preso relacionado: ");
        try {
            if (sistema.cadastrarCrime(new Crime(codigo,descricao,categoria,codigoPreso)))
                System.out.println("Crime registrado.");
            else System.out.println("Não foi possível registrar: código repetido ou preso inexistente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Dados inválidos: " + e.getMessage());
        }
    }

    private static void cadastrarVisita() {
        String codigo = lerTextoObrigatorio("Código da visita: ");
        String codigoPreso = lerTextoObrigatorio("Código do preso visitado: ");
        String visitante = lerTextoObrigatorio("Nome do visitante: ");
        String documento = lerTextoObrigatorio("Documento do visitante: ");
        String data = lerTextoObrigatorio("Data (dd/mm/aaaa): ");
        String horario = lerTextoObrigatorio("Horário: ");
        try {
            if (sistema.cadastrarVisita(new Visita(codigo,codigoPreso,visitante,documento,data,horario)))
                System.out.println("Visita agendada.");
            else System.out.println("Não foi possível agendar: código repetido ou preso inexistente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Dados inválidos: " + e.getMessage());
        }
    }

    private static void cadastrarRefeicao() {
        String codigo = lerTextoObrigatorio("Código da refeição: ");
        String tipo = lerTextoObrigatorio("Tipo (café/almoço/jantar): ");
        String descricao = lerTextoObrigatorio("Descrição do cardápio: ");
        String data = lerTextoObrigatorio("Data (dd/mm/aaaa): ");
        String horario = lerTextoObrigatorio("Horário: ");
        try {
            if (sistema.cadastrarRefeicao(new Refeicao(codigo,tipo,descricao,data,horario)))
                System.out.println("Refeição cadastrada.");
            else System.out.println("Já existe uma refeição com esse código.");
        } catch (IllegalArgumentException e) {
            System.out.println("Dados inválidos: " + e.getMessage());
        }
    }

    private static void listarPessoas() {
        System.out.println("\n--- Pessoas cadastradas ---");
        sistema.listarPessoas();
    }
    private static void listarCelas() {
        System.out.println("\n--- Celas ---");
        if (sistema.getCelas().isEmpty()) System.out.println("Nenhuma cela cadastrada.");
        for (Cela c : sistema.getCelas()) System.out.println(c.exibirDados());
    }
    private static void listarCrimes() {
        System.out.println("\n--- Crimes ---");
        if (sistema.getCrimes().isEmpty()) System.out.println("Nenhum crime registrado.");
        for (Crime c : sistema.getCrimes()) System.out.println(c.exibirDados());
    }
    private static void listarVisitas() {
        System.out.println("\n--- Visitas ---");
        if (sistema.getVisitas().isEmpty()) System.out.println("Nenhuma visita agendada.");
        for (Visita v : sistema.getVisitas()) System.out.println(v.exibirDados());
    }
    private static void listarRefeicoes() {
        System.out.println("\n--- Refeições ---");
        if (sistema.getRefeicoes().isEmpty()) System.out.println("Nenhuma refeição cadastrada.");
        for (Refeicao r : sistema.getRefeicoes()) System.out.println(r.exibirDados());
    }
    private static void buscarPreso() {
        String codigo = lerTexto("Digite o código do preso: ");
        Preso p = sistema.buscarPreso(codigo);
        if (p == null) System.out.println("Preso não encontrado.");
        else System.out.println(p.exibirDados());
    }
    private static void removerPreso() {
        String codigo = lerTexto("Código do preso que deseja excluir: ");
        if (sistema.removerPreso(codigo)) System.out.println("Preso e registros relacionados removidos.");
        else System.out.println("Preso não encontrado.");
    }

    private static void editarPreso() {
        String codigo = lerTextoObrigatorio("Código do preso que deseja editar: ");
        Preso atual = sistema.buscarPreso(codigo);
        if (atual == null) {
            System.out.println("Preso não encontrado.");
            return;
        }

        System.out.println("Pressione ENTER para manter o valor atual.");
        String nome = lerOpcional("Nome [" + atual.getNome() + "]: ", atual.getNome());
        String documento = lerOpcional("Documento [" + atual.getDocumento() + "]: ", atual.getDocumento());
        String telefone = lerOpcional("Telefone [" + atual.getTelefone() + "]: ", atual.getTelefone());
        String entrada = lerOpcional("Data de entrada [" + atual.getDataEntrada() + "]: ", atual.getDataEntrada());
        String cela = lerOpcional("Código da cela [" + atual.getCodigoCela() + "]: ", atual.getCodigoCela());
        String situacao = lerOpcional("Situação [" + atual.getSituacao() + "]: ", atual.getSituacao());

        try {
            Preso atualizado = new Preso(atual.getCodigo(), nome, documento, telefone, entrada, cela, situacao);
            if (sistema.editarPreso(codigo, atualizado)) System.out.println("Cadastro do preso atualizado.");
            else System.out.println("Edição não realizada: cela inexistente ou sem vagas.");
        } catch (IllegalArgumentException e) {
            System.out.println("Dados inválidos: " + e.getMessage());
        }
    }

    private static void relatorioOcupacao() {
        System.out.println("\n--- Ocupação das celas ---");
        if (sistema.getCelas().isEmpty()) {
            System.out.println("Nenhuma cela cadastrada.");
            return;
        }
        for (Cela cela : sistema.getCelas()) {
            System.out.println(cela.exibirDados() + (cela.temVaga() ? " | Vagas disponíveis" : " | Lotada"));
        }
    }

    private static void listarRegistrosTematicos() {
        System.out.println("\n--- Registros temáticos ---");
        sistema.listarRegistrosTematicos();
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine().trim();
    }
    private static String lerTextoObrigatorio(String mensagem) {
        while (true) {
            String valor = lerTexto(mensagem);
            if (!valor.isEmpty()) return valor;
            System.out.println("Este campo é obrigatório.");
        }
    }
    private static String lerOpcional(String mensagem, String atual) {
        String valor = lerTexto(mensagem);
        return valor.isEmpty() ? atual : valor;
    }
    private static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = sc.nextLine().trim();
            try { return Integer.parseInt(entrada); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro válido."); }
        }
    }
    private static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        sc.nextLine();
    }
}
