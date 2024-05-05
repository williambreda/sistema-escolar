package br.com.sistemaescolar.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
