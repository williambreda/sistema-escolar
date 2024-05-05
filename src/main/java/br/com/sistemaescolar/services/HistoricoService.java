package br.com.sistemaescolar.services;

import br.com.sistemaescolar.domain.historico.HistoricoResponseDTO;
import br.com.sistemaescolar.repositories.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoService {

    private final HistoricoRepository historicoRepository;

    @Autowired
    public HistoricoService(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public List<HistoricoResponseDTO> getHistoricoByNome(String nome) {
        List<Object[]> historicoList = historicoRepository.findHistoricoByNomeAluno(nome);
        return historicoList.stream()
                .map(objects -> new HistoricoResponseDTO((String) objects[0], (String) objects[1], (BigDecimal) objects[2]))
                .collect(Collectors.toList());
    }
}
