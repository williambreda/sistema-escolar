package br.com.sistemaescolar.domain.nota;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "nota")
@Entity(name = "nota")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Double valor;

    private String alunoid;

    private String disciplinaid;

    public Nota(NotaRequestDTO data){
        this.valor = data.valor();
        this.alunoid = data.alunoid();
        this.disciplinaid = data.disciplinaid();
    }
}