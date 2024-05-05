package br.com.sistemaescolar.controllers;

import br.com.sistemaescolar.domain.disciplina.Disciplina;
import br.com.sistemaescolar.domain.disciplina.DisciplinaRequestDTO;
import br.com.sistemaescolar.domain.disciplina.DisciplinaResponseDTO;
import br.com.sistemaescolar.repositories.AlunoRepository;
import br.com.sistemaescolar.repositories.DisciplinaRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Disciplinas", description = "API's para cadastro de disciplinas")
@RestController()
@RequestMapping("disciplina")
public class DisciplinaController {

    private final DisciplinaRepository repository;

    @Autowired
    public DisciplinaController(DisciplinaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity postDisciplina(@RequestBody @Valid DisciplinaRequestDTO body){
        Disciplina newDisciplina = new Disciplina(body);

        this.repository.save(newDisciplina);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getTodasDisciplinas(){
        List<DisciplinaResponseDTO> disciplinaList = this.repository.findAll().stream().map(DisciplinaResponseDTO::new).toList();

        return ResponseEntity.ok(disciplinaList);
    }
}
