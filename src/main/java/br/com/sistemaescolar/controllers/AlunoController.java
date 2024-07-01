package br.com.sistemaescolar.controllers;

import br.com.sistemaescolar.domain.aluno.Aluno;
import br.com.sistemaescolar.domain.aluno.AlunoRequestDTO;
import br.com.sistemaescolar.domain.aluno.AlunoResponseDTO;
import br.com.sistemaescolar.exceptions.Forbiden;
import br.com.sistemaescolar.repositories.AlunoRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Alunos", description = "API's para cadastro de alunos")
@RestController()
@RequestMapping("aluno")
public class AlunoController {

    private final AlunoRepository repository;

    @Autowired
    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o objeto ExemploResponse"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    @PostMapping
    public ResponseEntity postAluno(@RequestBody @Valid AlunoRequestDTO body){

        Aluno newAluno = new Aluno(body);
        this.repository.save(newAluno);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteAluno(@RequestBody @Valid AlunoRequestDTO body){

        //lógica para o delete

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateAluno(@RequestBody @Valid AlunoRequestDTO body){

        //lógica para o update

        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o objeto Aluno", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "403", description = "Não autorizado", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Forbiden.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    @GetMapping
    public ResponseEntity getTodosAlunos(){

        List<AlunoResponseDTO> alunosList = this.repository.findAll().stream().map(AlunoResponseDTO::new).toList();
        return ResponseEntity.ok(alunosList);
    }
}
