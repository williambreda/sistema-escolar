package br.com.sistemaescolar.domain.nota;

public record NotaResponseDTO(String id, Double valor, String alunoid, String disciplinaid) {
    public NotaResponseDTO(Nota nota){
        this(nota.getId(), nota.getValor(), nota.getAlunoid(), nota.getDisciplinaid());
    }
}
