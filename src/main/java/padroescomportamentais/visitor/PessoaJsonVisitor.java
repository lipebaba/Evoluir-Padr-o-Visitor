package padroescomportamentais.visitor;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PessoaJsonVisitor implements Visitor {

    private static final JsonNodeFactory JSON = JsonNodeFactory.instance;

    public String exibir(Pessoa pessoa) {
        return pessoa.aceitar(this);
    }

    @Override
    public String exibirAluno(Aluno aluno) {
        return dados("aluno", aluno.getNome())
                .put("matricula", aluno.getMatricula())
                .put("curso", aluno.getNomeCurso())
                .toString();
    }

    @Override
    public String exibirProfessor(Professor professor) {
        return dados("professor", professor.getNome())
                .put("matricula", professor.getMatricula())
                .put("titulacao", professor.getTitulacao())
                .toString();
    }

    @Override
    public String exibirFuncionario(Funcionario funcionario) {
        float salario = funcionario.getSalario();
        if (!Float.isFinite(salario)) {
            throw new IllegalArgumentException("O salário deve ser um número finito.");
        }
        return dados("funcionario", funcionario.getNome())
                .put("codigo", funcionario.getCodigo())
                .put("salario", salario)
                .toString();
    }

    private ObjectNode dados(String tipo, String nome) {
        return JSON.objectNode().put("tipo", tipo).put("nome", nome);
    }
}
