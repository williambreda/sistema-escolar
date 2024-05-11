package br.com.sistemaescolar.tests;

import br.com.sistemaescolar.data.CsvEntryRegister;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.concurrent.ExecutionException;

import static br.com.sistemaescolar.helpers.BuildHelpers.buildPojo;
import static br.com.sistemaescolar.helpers.RequestHelpers.asyncRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags({@Tag("migrate"),@Tag("toggle")})
@DisplayName("Testes Migração Entre Clusters Topaz Toggle")
public class TempTest {
    CsvEntryRegister csv = new CsvEntryRegister();
    @ParameterizedTest
    @CsvFileSource(resources = "/auth-register/auth-register.csv", numLinesToSkip = 1)
    @DisplayName("Register Tests")
    void migrate(ArgumentsAccessor accessor) throws ExecutionException, InterruptedException {
        var teste = buildPojo.apply("auth-register", "William Admin", "123", "ADMIN", null);
        var request = asyncRequest.apply(teste);
//        Try.run(() -> System.out.println(request.get().body()));
//        Try.run(() -> System.out.println(request.get().statusCode()));
        System.out.println(System.getProperty("base.url"));


    }

}
