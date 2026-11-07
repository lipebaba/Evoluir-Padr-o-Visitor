package padroescomportamentais.visitor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CadastroPessoasTest {

    private final ObjectMapper json = new ObjectMapper();

    @Test
    void deveExportarListaMistaNaOrdemDoCadastro() throws Exception {
        Pessoa aluno = new Aluno(1, "Ana", new Curso("Computação"));
        Pessoa professor = new Professor(2, "Maria", "Mestrado");
        Pessoa funcionario = new Funcionario(3, "Pedro", 0.0f);
        CadastroPessoas cadastro = new CadastroPessoas(List.of(aluno, professor, funcionario));

        JsonNode resultado = json.readTree(cadastro.exportarJson());
        assertTrue(resultado.isArray());
        assertEquals(3, resultado.size());
        assertEquals("aluno", resultado.get(0).path("tipo").asText());
        assertEquals("professor", resultado.get(1).path("tipo").asText());
        assertEquals("funcionario", resultado.get(2).path("tipo").asText());
        assertEquals(0.0, resultado.get(2).path("salario").asDouble());
    }

    @Test
    void deveExportarListaVazia() {
        assertEquals("[]", new CadastroPessoas(List.of()).exportarJson());
    }

    @Test
    void deveAplicarVisitantesDiferentesAoMesmoCadastro() throws Exception {
        Pessoa aluno = new Aluno(1, "Ana", new Curso("Computação"));
        CadastroPessoas cadastro = new CadastroPessoas(List.of(aluno));

        assertEquals(List.of("Aluno{matricula=1, nome='Ana', curso=Computação}"),
                cadastro.exibir(new PessoaVisitor()));
        assertEquals("Ana", json.readTree(cadastro.exportarJson()).get(0).path("nome").asText());
    }

    @Test
    void deveGravarArquivoDaAplicacao(@TempDir Path pasta) throws Exception {
        Path arquivo = pasta.resolve("pessoas.json");
        Principal.main(new String[]{arquivo.toString()});
        try (var exemplo = getClass().getResourceAsStream("/pessoas.json")) {
            assertNotNull(exemplo);
            assertEquals(json.readTree(exemplo), json.readTree(Files.readString(arquivo)));
        }
    }
}
