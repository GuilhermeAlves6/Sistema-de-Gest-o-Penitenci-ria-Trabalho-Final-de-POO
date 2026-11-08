# Tabela de rastreabilidade

As linhas abaixo apontam para as implementações no código-fonte desta pasta.

| Requisito | Localização exata | Evidência |
|---|---|---|
| Classe abstrata, método abstrato e concreto | `Pessoa.java`, linhas 2, 25 e 27 | `Pessoa` declara `getTipo()` abstrato e implementa `exibirDados()`. |
| Duas subclasses concretas | `Preso.java:2`; `PolicialPenal.java:2` | Ambas estendem `Pessoa`; sobrescritas em `Preso.java:28` e `PolicialPenal.java:22`. |
| Segunda hierarquia | `RegistroPenitenciario.java:2,14-15`; `Crime.java:1`, `Visita.java:1`, `Refeicao.java:1` | Classe abstrata e três subclasses concretas. |
| Polimorfismo | `Penitenciaria.java:128,140` | Listas da superclasse `Pessoa` e `RegistroPenitenciario` chamam `exibirDados()`. |
| Encapsulamento e validação | `Pessoa.java:3-4,21-23`; `Cela.java:19-32` | Atributos privados e setters que rejeitam campos vazios e ocupação/capacidade inválidas. |
| Leitura e escrita TXT | `Armazenamento.java:22,37`; `Penitenciaria.java:13,153,188` | Leitura/escrita, carregamento ao iniciar e salvamento ao alterar dados. |
| Menu em loop e entrada inválida | `Principal.java:8,41,234-251` | Laço de interação, opções e rejeição de texto vazio/número inválido. |
| Cadastrar, listar, buscar, editar e remover | `Principal.java:13-29,41-58,62,153,177,183,189` | Menu encaminha para as operações; a edição mantém as vagas consistentes. |
| Opção específica do tema | `Principal.java:28,214` | Relatório de ocupação e disponibilidade das celas. |
| Lista polimórfica dos registros temáticos | `Principal.java:29`; `Penitenciaria.java:140` | Lista crimes, visitas e refeições pela superclasse comum. |
| Arquivos TXT de exemplo | `dados/celas.txt:1`, `dados/crimes.txt:1`, `dados/policiais.txt:1`, `dados/presos.txt:1`, `dados/refeicoes.txt:1`, `dados/visitas.txt:1` | Um registro de exemplo por arquivo persistido. |
| Diagrama atualizado | `DIAGRAMA.md:1-103` | Classes, hierarquias, associações e justificativa. |
