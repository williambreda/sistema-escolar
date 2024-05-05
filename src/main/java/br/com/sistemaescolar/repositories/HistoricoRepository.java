package br.com.sistemaescolar.repositories;

import br.com.sistemaescolar.domain.historico.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {

    @Query(value = "SELECT nomealuno, nomedisciplina, valor FROM historico WHERE nomealuno = :nome", nativeQuery = true)
    List<Object[]> findHistoricoByNomeAluno(@Param("nome") String nome);
}
