package br.com.sistemaescolar.domain.disciplina;

public record DisciplinaResponseDTO(String id, String name) {
    public DisciplinaResponseDTO(Disciplina disciplina){
        this(disciplina.getId(), disciplina.getName());
    }
}
