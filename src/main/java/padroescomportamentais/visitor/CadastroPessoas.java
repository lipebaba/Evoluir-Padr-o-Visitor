package padroescomportamentais.visitor;

import java.util.List;
import java.util.stream.Collectors;

public class CadastroPessoas {

    private final List<Pessoa> pessoas;

    public CadastroPessoas(List<Pessoa> pessoas) {
        this.pessoas = List.copyOf(pessoas);
    }

    public List<String> exibir(Visitor visitor) {
        return pessoas.stream()
                .map(pessoa -> pessoa.aceitar(visitor))
                .collect(Collectors.toList());
    }

    public String exportarJson() {
        return "[" + String.join(",", exibir(new PessoaJsonVisitor())) + "]";
    }
}
