# Diagrama de classes

```mermaid
classDiagram
    class Pessoa {
        <<abstract>>
        -String codigo
        -String nome
        -String documento
        -String telefone
        +getTipo()* String
        +exibirDados() String
    }
    class Preso {
        -String dataEntrada
        -String codigoCela
        -String situacao
        +getTipo() String
        +exibirDados() String
    }
    class PolicialPenal {
        -String matricula
        -String turno
        +getTipo() String
        +exibirDados() String
    }
    Pessoa <|-- Preso
    Pessoa <|-- PolicialPenal

    class RegistroPenitenciario {
        <<abstract>>
        -String codigo
        +getTipo()* String
        +exibirDados()* String
        +identificacao() String
    }
    class Crime {
        -String descricao
        -String categoria
        -String codigoPreso
        +getTipo() String
        +exibirDados() String
    }
    class Visita {
        -String codigoPreso
        -String nomeVisitante
        -String documentoVisitante
        -String data
        -String horario
        +getTipo() String
        +exibirDados() String
    }
    class Refeicao {
        -String tipoRefeicao
        -String descricao
        -String data
        -String horario
        +getTipo() String
        +exibirDados() String
    }
    RegistroPenitenciario <|-- Crime
    RegistroPenitenciario <|-- Visita
    RegistroPenitenciario <|-- Refeicao

    class Cela {
        -String codigo
        -String bloco
        -int capacidade
        -int ocupacao
    }
    class Penitenciaria {
        -List~Preso~ presos
        -List~PolicialPenal~ policiais
        -List~Cela~ celas
        -List~Crime~ crimes
        -List~Visita~ visitas
        -List~Refeicao~ refeicoes
        +listarPessoas() void
        +editarPreso(codigo, atualizado) boolean
        +removerPreso(codigo) boolean
    }
    class Armazenamento {
        -Path pasta
        +ler(arquivo) List~String[]~
        +salvar(arquivo, linhas) void
    }
    Penitenciaria o-- Pessoa : administra presos e policiais
    Penitenciaria o-- Cela : administra
    Penitenciaria o-- RegistroPenitenciario : administra crimes, visitas e refeições
    Penitenciaria --> Armazenamento : carrega e persiste
    Preso --> Cela : alocado em
    Crime --> Preso : associado a
    Visita --> Preso : visita
```

**Justificativa das mudanças em relação à implementação-base:** foi criada a
hierarquia abstrata `RegistroPenitenciario` para que Crime, Visita e Refeicao
formem a segunda hierarquia exigida nesta etapa; `Pessoa` continua sendo a
hierarquia de pessoas. O diagrama representa as classes e associações atuais.

O diagrama da Etapa 1 não estava disponível nos arquivos fornecidos; compare
este diagrama com o original da equipe antes da entrega final e inclua outras
alterações se o diagrama anterior tiver classes ou relacionamentos diferentes.
