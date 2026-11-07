# Padrão Visitor Evoluido

Projeto desenvolvido para demonstrar o padrão de projeto **Visitor** em Java, como aplicação prática da disciplina Arquitetura e Projeto de Software.

A aplicação simula um cadastro acadêmico com alunos, professores e funcionários. O mesmo cadastro pode ser exibido em texto no console ou exportado em JSON.

## 📌 Funcionamento

Cada pessoa possui um nome e campos específicos de acordo com seu tipo:

| Tipo | Campos específicos |
| --- | --- |
| `Aluno` | Matrícula e curso |
| `Professor` | Matrícula e titulação |
| `Funcionario` | Código e salário |

A classe `Principal` cria um cadastro de exemplo, exibe os dados em texto e imprime o JSON no console. Quando recebe um caminho como argumento, também grava o JSON em um arquivo.

## 🧩 Padrão Visitor

O **Visitor** é um padrão comportamental que permite adicionar operações a um conjunto de classes. Neste projeto, as operações de exibição em texto e exportação em JSON ficam em visitantes separados.

As classes `Aluno`, `Professor` e `Funcionario` implementam a interface `Pessoa`. Cada uma recebe um `Visitor` pelo método `aceitar()` e chama o método correspondente ao seu tipo. Na classe `Aluno`, por exemplo:

```java
public String aceitar(Visitor visitor) {
    return visitor.exibirAluno(this);
}
```

A interface `Visitor` define um método para cada tipo de pessoa. O projeto tem duas implementações:

| Visitante | Operação |
| --- | --- |
| `PessoaVisitor` | Formata os dados de cada pessoa em texto |
| `PessoaJsonVisitor` | Converte os dados de cada pessoa em JSON com Jackson |

## 🔄 Fluxo da aplicação

1. A classe `Principal` cria um cadastro com um aluno, uma professora e um funcionário.
2. O `CadastroPessoas` percorre a lista e chama `aceitar()` em cada pessoa.
3. Cada pessoa direciona a chamada ao método correspondente do visitante.
4. O `PessoaVisitor` produz as linhas exibidas no console.
5. O `PessoaJsonVisitor` gera os objetos JSON, que o cadastro reúne em uma lista na ordem original.

## 📄 Exemplo de JSON

```json
[
  {
    "tipo": "aluno",
    "nome": "Ana",
    "matricula": 101,
    "curso": "Engenharia de Software"
  },
  {
    "tipo": "professor",
    "nome": "Maria",
    "matricula": 202,
    "titulacao": "Doutorado"
  },
  {
    "tipo": "funcionario",
    "nome": "Pedro",
    "codigo": 303,
    "salario": 5000.5
  }
]
```

O campo `tipo` identifica a categoria de cada pessoa. Matrícula, código e salário são exportados como números. O JSON usado nos testes está em `src/test/resources/pessoas.json`.

## 📁 Estrutura do projeto

```text
visitor-expandido/
├── src/
│   ├── main/java/padroescomportamentais/visitor/
│   │   ├── Aluno.java
│   │   ├── CadastroPessoas.java
│   │   ├── Curso.java
│   │   ├── Funcionario.java
│   │   ├── Pessoa.java
│   │   ├── PessoaJsonVisitor.java
│   │   ├── PessoaVisitor.java
│   │   ├── Principal.java
│   │   ├── Professor.java
│   │   └── Visitor.java
│   └── test/
│       ├── java/padroescomportamentais/visitor/
│       │   ├── CadastroPessoasTest.java
│       │   ├── PessoaJsonVisitorTest.java
│       │   └── PessoaVisitorTest.java
│       └── resources/
│           └── pessoas.json
├── .gitignore
├── pom.xml
└── README.md
```

## 🚀 Como executar

É necessário ter o JDK 11 ou superior e o Maven instalados. Abra o terminal na pasta que contém o `pom.xml` e execute:

```bash
mvn compile org.codehaus.mojo:exec-maven-plugin:3.5.0:java "-Dexec.mainClass=padroescomportamentais.visitor.Principal" "-Dexec.args=pessoas.json"
```

O comando exibe o cadastro no console e grava `pessoas.json` na pasta do projeto. Para exibir os dados apenas no console, retire o argumento `"-Dexec.args=pessoas.json"`.

No IntelliJ IDEA, abra o `pom.xml` como projeto Maven e execute a classe `Principal`. Para salvar o JSON, informe `pessoas.json` no campo **Program arguments** da configuração de execução.

## ✅ Testes automatizados

O projeto tem **12 testes** com JUnit 5:

| Classe | Testes | Verificação |
| --- | ---: | --- |
| `PessoaVisitorTest` | 3 | Saída em texto para aluno, professor e funcionário |
| `PessoaJsonVisitorTest` | 5 | Campos de cada tipo, salário numérico, caracteres especiais e rejeição de salários não finitos |
| `CadastroPessoasTest` | 4 | Lista vazia, ordem dos registros, uso dos dois visitantes e gravação do arquivo JSON |

Para executar os testes:

```bash
mvn test
```

Os 12 testes passaram na validação com o executor do JUnit. O código foi compilado com `javac --release 11`.

## 🛠️ Tecnologias utilizadas

- Java 11
- Maven
- Jackson Databind 2.19.2
- JUnit 5.11.4
- Maven Surefire Plugin 3.5.2

## 🎓 Objetivo acadêmico

Demonstrar como o padrão Visitor permite aplicar operações diferentes ao mesmo conjunto de objetos. A exportação em JSON foi adicionada por meio de um novo visitante, usando o método `aceitar()` que já existia nas classes do cadastro.

Projeto baseado no exemplo de Visitor do repositório [Padrões de Projeto](https://github.com/marcoaparaujo/padroes-projeto).

## 👤 Autor

**Felipe Baba**

Projeto desenvolvido para fins acadêmicos na disciplina Arquitetura e Projeto de Software.