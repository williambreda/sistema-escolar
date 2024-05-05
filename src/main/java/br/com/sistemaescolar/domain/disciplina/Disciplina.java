package br.com.sistemaescolar.domain.disciplina;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "disciplinas")
@Entity(name = "disciplinas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    public Disciplina(DisciplinaRequestDTO data){
        this.name = data.name();
    }
}