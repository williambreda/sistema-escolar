package br.com.sistemaescolar.tests;

import br.com.sistemaescolar.data.CsvEntryRegister;
import com.epam.reportportal.service.ReportPortal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static br.com.sistemaescolar.helpers.BuildHelpers.buildPojo;
import static br.com.sistemaescolar.helpers.BuildHelpers.retornaUserDinamico;
import static br.com.sistemaescolar.helpers.RequestHelpers.asyncRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags({@Tag("register"),@Tag("integracao")})
@DisplayName("Testes Cadastro de Usuários")
public class RegisterTest {
    CsvEntryRegister csv = new CsvEntryRegister();
    @ParameterizedTest
    @CsvFileSource(resources = "/auth-register/auth-register.csv", numLinesToSkip = 1)
    @DisplayName("Register Tests")
    void register(ArgumentsAccessor accessor) throws ExecutionException, InterruptedException {
        var csvEntry = csv.mapToCsvEntry(accessor);
        var pojo = buildPojo.apply("auth-register", csvEntry.getCenario().equalsIgnoreCase("ok")
                ?retornaUserDinamico.apply(csvEntry.getLogin())
                :csvEntry.getLogin(), csvEntry.getPassword(), csvEntry.getRole(), null);

        var request = asyncRequest.apply(pojo);

        Assertions.assertEquals(request.get().statusCode(), Integer.valueOf(csvEntry.getStatus()));

        ReportPortal.emitLog("teste", "info", Calendar.getInstance().getTime());

    }
}
