package br.com.sistemaescolar.repositories;

import br.com.sistemaescolar.domain.nota.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, String> {

    @Query(value = "SELECT * FROM nota WHERE disciplinaid = :disciplinaid", nativeQuery = true)
    List<Nota> findByFiltroId(String disciplinaid);

}
