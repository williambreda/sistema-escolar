package br.com.sistemaescolar.repositories;

import br.com.sistemaescolar.domain.aluno.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, String> {
}
