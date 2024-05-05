package br.com.sistemaescolar;

import br.com.sistemaescolar.controllers.AlunoController;
import br.com.sistemaescolar.domain.aluno.Aluno;
import br.com.sistemaescolar.domain.aluno.AlunoRequestDTO;
import br.com.sistemaescolar.domain.aluno.AlunoResponseDTO;
import br.com.sistemaescolar.repositories.AlunoRepository;
import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlunoControllerTest {

    @Test
    @Tags({@Tag("aluno"),@Tag("todos"), @Tag("post")})
    @DisplayName("Teste da API: POST /aluno")
    public void testPostAluno() {
        AlunoRepository repository = mock(AlunoRepository.class);
        AlunoRequestDTO alunoRequestDTO = new AlunoRequestDTO("William Teste");
        AlunoController seuControlador = new AlunoController(repository);
        ResponseEntity responseEntity = seuControlador.postAluno(alunoRequestDTO);
        verify(repository, times(1)).save(argThat(argument -> true));
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Tags({@Tag("aluno"),@Tag("todos"), @Tag("get")})
    @DisplayName("Teste da API: GET /aluno")
    public void testGetTodosAlunos() {
        AlunoRepository repository = mock(AlunoRepository.class);

        var id1 = UUID.randomUUID().toString();
        var name1 = "William Teste 1";
        var id2 = UUID.randomUUID().toString();
        var name2 = "William Teste 2";

        Aluno aluno1 = new Aluno(id1, name1);
        Aluno aluno2 = new Aluno(id2, name2);
        List<Aluno> alunos = Arrays.asList(aluno1, aluno2);

        when(repository.findAll()).thenReturn(alunos);
        AlunoController seuControlador = new AlunoController(repository);
        ResponseEntity responseEntity = seuControlador.getTodosAlunos();
        assertEquals(200, responseEntity.getStatusCodeValue());

        List<AlunoResponseDTO> alunosList = (List<AlunoResponseDTO>) responseEntity.getBody();
        assert alunosList != null;
        assertEquals(2, alunosList.size());

        System.out.println(id1 + " " + alunosList.get(0).id());
        System.out.println(id2 + " " + alunosList.get(1).id());

        assertEquals(id1, alunosList.get(0).id());
        assertEquals(id2, alunosList.get(1).id());
        assertEquals(name1, alunosList.get(0).name());
        assertEquals(name2, alunosList.get(1).name());
    }
}
