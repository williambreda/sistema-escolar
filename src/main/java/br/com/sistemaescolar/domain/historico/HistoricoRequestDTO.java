package br.com.sistemaescolar.domain.historico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record HistoricoRequestDTO(
        @NotBlank
        String nomealuno,
        @NotBlank
        String nomedisciplina,
        @NotNull
        BigDecimal valor

) {
}
