package padroescomportamentais.visitor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaJsonVisitorTest {

    private final PessoaJsonVisitor visitor = new PessoaJsonVisitor();
    private final ObjectMapper json = new ObjectMapper();

    @Test
    void deveExportarAluno() throws Exception {
        Pessoa pessoa = new Aluno(101, "Ana", new Curso("Engenharia de Software"));
        assertEquals(json.readTree("{\"tipo\":\"aluno\",\"nome\":\"Ana\",\"matricula\":101,"
                + "\"curso\":\"Engenharia de Software\"}"), json.readTree(visitor.exibir(pessoa)));
    }

    @Test
    void deveExportarProfessor() throws Exception {
        Pessoa pessoa = new Professor(202, "Maria", "Doutorado");
        assertEquals(json.readTree("{\"tipo\":\"professor\",\"nome\":\"Maria\",\"matricula\":202,"
                + "\"titulacao\":\"Doutorado\"}"), json.readTree(visitor.exibir(pessoa)));
    }

    @Test
    void deveExportarFuncionarioComSalarioNumerico() throws Exception {
        Pessoa pessoa = new Funcionario(303, "Pedro", 5000.50f);
        JsonNode resultado = json.readTree(visitor.exibir(pessoa));
        assertEquals(json.readTree("{\"tipo\":\"funcionario\",\"nome\":\"Pedro\",\"codigo\":303,"
                + "\"salario\":5000.50}"), resultado);
        assertTrue(resultado.path("salario").isNumber());
    }

    @Test
    void devePreservarAcentosAspasBarraEQuebraDeLinha() throws Exception {
        String nome = "João \"Júnior\" \\ Silva\nSouza";
        Pessoa pessoa = new Aluno(1, nome, new Curso("Computação"));
        JsonNode resultado = json.readTree(visitor.exibir(pessoa));
        assertEquals(nome, resultado.path("nome").asText());
        assertEquals("Computação", resultado.path("curso").asText());
    }

    @Test
    void deveRejeitarSalariosNaoFinitos() {
        for (float salario : new float[]{Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY}) {
            Pessoa pessoa = new Funcionario(1, "Pedro", salario);
            assertThrows(IllegalArgumentException.class, () -> visitor.exibir(pessoa));
        }
    }
}
