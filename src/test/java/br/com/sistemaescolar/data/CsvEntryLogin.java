package br.com.sistemaescolar.data;

import lombok.SneakyThrows;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

public class CsvEntryLogin {
    private String nome;
    private String cenario;
    private String login;
    private String password;
    private String status;
    private String message;


    @SneakyThrows
    public CsvEntryLogin mapToCsvEntry(ArgumentsAccessor accessor) {

        String nome = accessor.getString(0);
        String cenario = accessor.getString(1);
        String login = accessor.getString(2);
        String password = accessor.getString(3);
        String status = accessor.getString(4);
        String message = accessor.getString(5);

        return new CsvEntryLogin(nome, cenario, login, password, status, message);
    }

    public CsvEntryLogin() {
    }


    public CsvEntryLogin(String nome, String cenario, String login, String password, String status, String message) {
        this.nome = nome;
        this.cenario = cenario;
        this.login = login;
        this.password = password;
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
