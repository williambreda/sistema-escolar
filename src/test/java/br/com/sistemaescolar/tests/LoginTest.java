package br.com.sistemaescolar.tests;

import br.com.sistemaescolar.data.CsvEntryLogin;
import com.epam.reportportal.service.ReportPortal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static br.com.sistemaescolar.helpers.BuildHelpers.buildPojo;
import static br.com.sistemaescolar.helpers.RequestHelpers.asyncRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags({@Tag("login"),@Tag("integracao")})
@DisplayName("Testes Login de Usuários")
public class LoginTest {
    CsvEntryLogin csv = new CsvEntryLogin();
    @ParameterizedTest
    @CsvFileSource(resources = "/auth-login/auth-login.csv", numLinesToSkip = 1)
    @DisplayName("Login Tests")
    void login(ArgumentsAccessor accessor) throws ExecutionException, InterruptedException {
        var csvEntry = csv.mapToCsvEntry(accessor);
        var pojo = buildPojo.apply("auth-login", csvEntry.getLogin(),
                csvEntry.getPassword(), null, null);

        var request = asyncRequest.apply(pojo);

        Assertions.assertEquals(request.get().statusCode(), Integer.valueOf(csvEntry.getStatus()));

    }
}
