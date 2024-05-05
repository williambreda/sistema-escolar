package br.com.sistemaescolar.repositories;

import br.com.sistemaescolar.domain.disciplina.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, String> {
}
