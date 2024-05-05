package br.com.sistemaescolar.domain.historico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "historico")
@Entity(name = "historico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomealuno;

    private String nomedisciplina;

    private BigDecimal valor;

    public Historico(HistoricoRequestDTO data){
        this.nomealuno = data.nomealuno();
        this.nomedisciplina = data.nomedisciplina();
        this.valor = data.valor();
    }
}