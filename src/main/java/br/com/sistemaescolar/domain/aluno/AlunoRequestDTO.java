package br.com.sistemaescolar.domain.aluno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoRequestDTO(
        @NotBlank
        String name

) {
}
