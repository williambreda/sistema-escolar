package br.com.sistemaescolar.domain.aluno;

public record AlunoResponseDTO(String id, String name) {
    public AlunoResponseDTO(Aluno aluno){
        this(aluno.getId(), aluno.getName());
    }
}
