package br.com.sistemaescolar.domain.nota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotaRequestDTO(
        @NotNull
        Double valor,
        @NotBlank
        String alunoid,
        @NotBlank
        String disciplinaid

) {
}
