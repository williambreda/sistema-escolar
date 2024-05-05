package br.com.sistemaescolar.domain.historico;

import java.math.BigDecimal;

public record HistoricoResponseDTO(String nomealuno, String nomedisciplina, BigDecimal valor) {
    public HistoricoResponseDTO(Historico historico){
        this(historico.getNomealuno(), historico.getNomedisciplina(), historico.getValor());
    }
}