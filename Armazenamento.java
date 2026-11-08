import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

// [ARQUIVOS TXT] Centraliza leitura e escrita para manter os dados entre execuções.
public class Armazenamento {
    private final Path pasta = Paths.get("dados");

    public Armazenamento() {
        try {
            Files.createDirectories(pasta);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível criar a pasta de dados: " + e.getMessage());
        }
    }

    public List<String[]> ler(String arquivo) {
        List<String[]> registros = new ArrayList<>();
        Path caminho = pasta.resolve(arquivo);
        if (!Files.exists(caminho)) return registros;
        try {
            for (String linha : Files.readAllLines(caminho, StandardCharsets.UTF_8)) {
                // O limite negativo preserva campos vazios, como preso ainda sem cela.
                if (!linha.trim().isEmpty()) registros.add(linha.split("\\|", -1));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler " + arquivo + ": " + e.getMessage());
        }
        return registros;
    }

    public void salvar(String arquivo, List<String> linhas) {
        Path caminho = pasta.resolve(arquivo);
        try {
            Files.write(caminho, linhas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Erro ao salvar " + arquivo + ": " + e.getMessage());
        }
    }
}
