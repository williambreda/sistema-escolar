package br.com.sistemaescolar.controllers;

import br.com.sistemaescolar.domain.historico.HistoricoResponseDTO;
import br.com.sistemaescolar.services.HistoricoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Histórico", description = "API's para consulta do histórico de notas")
@RestController
public class HistoricoController {

    private final HistoricoService historicoService;

    @Autowired
    public HistoricoController(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @GetMapping("/historico")
    public ResponseEntity<List<HistoricoResponseDTO>> getHistorico(@RequestParam String nome) {
        List<HistoricoResponseDTO> historicoList = historicoService.getHistoricoByNome(nome);
        return ResponseEntity.ok(historicoList);
    }
}
