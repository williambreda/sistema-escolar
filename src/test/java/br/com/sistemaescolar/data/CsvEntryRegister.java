package br.com.sistemaescolar.data;

import lombok.SneakyThrows;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

public class CsvEntryRegister {
    private String nome;
    private String cenario;
    private String login;
    private String password;
    private String role;
    private String status;
    private String message;


    @SneakyThrows
    public CsvEntryRegister mapToCsvEntry(ArgumentsAccessor accessor) {

        String nome = accessor.getString(0);
        String cenario = accessor.getString(1);
        String login = accessor.getString(2);
        String password = accessor.getString(3);
        String role = accessor.getString(4);
        String status = accessor.getString(5);
        String message = accessor.getString(6);

        return new CsvEntryRegister(nome, cenario, login, password, role, status, message);
    }

    public CsvEntryRegister() {
    }


    public CsvEntryRegister(String nome, String cenario, String login, String password, String role, String status, String message) {
        this.nome = nome;
        this.cenario = cenario;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
        this.message = message;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCenario() {
        return cenario;
    }

    public void setCenario(String cenario) {
        this.cenario = cenario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
