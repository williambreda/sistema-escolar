package br.com.sistemaescolar.domain.disciplina;

import jakarta.validation.constraints.NotBlank;

public record DisciplinaRequestDTO(
        @NotBlank
        String name

) {
}
