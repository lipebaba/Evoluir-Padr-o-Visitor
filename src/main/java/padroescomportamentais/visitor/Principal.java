package padroescomportamentais.visitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Principal {

    public static void main(String[] args) throws IOException {
        CadastroPessoas cadastro = new CadastroPessoas(List.of(
                new Aluno(101, "Ana", new Curso("Engenharia de Software")),
                new Professor(202, "Maria", "Doutorado"),
                new Funcionario(303, "Pedro", 5000.50f)
        ));

        cadastro.exibir(new PessoaVisitor()).forEach(System.out::println);
        String json = cadastro.exportarJson();
        System.out.println(json);
        if (args.length > 0) {
            Files.writeString(Path.of(args[0]), json);
        }
    }
}
