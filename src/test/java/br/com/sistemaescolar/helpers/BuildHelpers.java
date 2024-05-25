package br.com.sistemaescolar.helpers;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.sistemaescolar.data.Api2POJO;
import io.vavr.Function5;
import io.vavr.control.Try;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public interface BuildHelpers {

    Function<Map<String, String>, List<String>> mapToList = map -> {
        var aList = new ArrayList<String>();
        map.forEach((key, value) -> aList.addAll(List.of(key, value)));
        return aList;
    };

    Function<String[], Map<String, String>> arrayToMap = list -> {
        Map<String, String> heads = new HashMap<>();
        IntStream.range(0, list.length).forEach(f -> {
            if (f % 2 == 0) heads.put(list[f], list[f + 1]);
        });
        return heads;
    };

    BiFunction<String, String, String> pathFunc = (feature, type) -> {
        var ext = type.contains("body") ? "-body.json" : ".json";
        var path = Paths.get("src", "test", "resources", feature, feature.concat(ext));
        return Files.exists(path) ? path.toString() : "path não encontrado para " + feature;
    };

    BiFunction<String, String, String> pathFuncMovement = (feature, type) -> {
        var ext = type.contains("body") ? "-body.json" : ".json";
        var path = Paths.get("src", "test", "resources", "movement", feature, feature.concat(ext));
        return Files.exists(path) ? path.toString() : "path não encontrado para " + feature;
    };

    BiFunction<Api2POJO, Map<String, String>, Api2POJO> updateHeaders = (api2POJO, headers) -> {

        var array = api2POJO.getHeaders();
        var heads = arrayToMap.apply(array);
        heads.putAll(headers);

        api2POJO.setHeaders(mapToList.apply(heads).toArray(new String[0]));

        return api2POJO;
    };

    Supplier<Map<String, String>> transactionIDHeaders = () -> Map.of("Topaz-Transaction-ID", UUID.randomUUID().toString());

    BiFunction<Api2POJO, Map<String, String>, Api2POJO> updateQueryParams = (api2POJO, params) -> {

        var array = api2POJO.getQueryParams();
        var mapParams = arrayToMap.apply(array);
        mapParams.putAll(params);
        api2POJO.setQueryParams(mapToList.apply(mapParams).toArray(new String[0]));

        return api2POJO;
    };

    Function<String, Map<String, String>> stringToMap = (string) -> {
        var objectMapper = new ObjectMapper();
        objectMapper.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature());
        var map = Try.of(() -> objectMapper.readValue(string, Map.class)).onFailure(Throwable::printStackTrace).getOrElse(Map.of());

        map.forEach((k, v) -> {
            map.put(k, String.valueOf(v));
        });

        return map;
    };

    Function<String, Api2POJO> apiConfFromPojo = apiJsonFile -> {
        var pojo = Try.of(() -> new ObjectMapper().readValue(Files.newBufferedReader(Paths.get(apiJsonFile)), Api2POJO.class))
                .onFailure(Throwable::printStackTrace)
                .get();

        var envAmbiente = System.getProperty("base.url");

        if (envAmbiente != null) {
            pojo.setBaseUrl(envAmbiente.contains("http://") ? envAmbiente : "http://".concat(envAmbiente));
        }

        return pojo;
    };

    Function<Map<String, String>, Map<String, String>> buildValues = mapa -> {
        var objectMapper = new ObjectMapper();
        objectMapper.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature());

        Map<String, String> values = new HashMap<>();
        values.putAll(mapa);

        return values;
    };

    Function<Map<String, String>, Map<String, String>> buildValuesCashin = mapa -> {
        var objectMapper = new ObjectMapper();
        objectMapper.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature());
        Map<String, String> values = new HashMap<>();
        values.putAll(mapa);

        return values;
    };



    BiFunction<Map<String, String>, String, String> updateBody = (map, jsonBodyFile) ->
            Try.of(() -> {

                        var payload = new ObjectMapper().readValue(Files.newBufferedReader(Paths.get(jsonBodyFile)), Map.class);
                        map.keySet().stream().forEach(i -> {

                            if (i.equalsIgnoreCase("account")) {
                                Map<String, Map> values = new HashMap<>();
                                Map<String, String> account = new HashMap<>();
                                account.putAll(Map.of("accountNumber", map.get("account"), "bankBranch", "1", "accountType", "101"));
                                values.put("account", account);
                                payload.put(i, account);
                            } else {
                                payload.put(i, map.get(i));
                            }
                        });
                        System.out.println(payload);
                        return Try.of(() -> new ObjectMapper().writeValueAsString(payload)).getOrElse("{}");
                    })
                    .getOrElse("Não foi possível ler o arquivo de referência ".concat(jsonBodyFile));

    Function5<String, String, String, String, String, Api2POJO> buildPojo = (feature, param1, param2, param3, param4) -> {

        var pojo = apiConfFromPojo.apply(pathFunc.apply(feature, "json"));

        switch (feature) {
            case "auth-register" -> pojo.setBody(updateBody.apply(buildValues.apply(Map.of("login", param1, "password", param2, "role", param3)), pathFunc.apply(feature, "body")));
            case "auth-login" -> pojo.setBody(updateBody.apply(buildValues.apply(Map.of("login", param1, "password", param2)), pathFunc.apply(feature, "body")));
            case "add-blacklist", "add-greylist" -> pojo.setBody("[" + updateBody.apply(buildValues.apply(Map.of("customerId", param1, "reason", param2)), pathFunc.apply(feature, "body")) + "]");
            case "criar-customer", "adquirencia-cashin" -> pojo.setBody(updateBody.apply(buildValues.apply(Map.of("email", param1)), pathFunc.apply(feature, "body")));
            case "migrar-customer" -> {
                pojo.setBody("[" + "\"" + param1 + "\"" + "]");
                if(param2 != null) updateHeaders.apply(pojo, Map.of("Account-Group", param2));
                if(param3 != null) updateHeaders.apply(pojo, Map.of("x-force-cluster", param3)); }
            case "topaz-balance" -> { updateQueryParams.apply(pojo, Map.of("accountNumber", param1));
                                      if(param2.equalsIgnoreCase("C2")) pojo.setBaseUrl("http://10.188.25.172:8080"); }
            case "status-customer" , "desmigrar-customer", "reset-customer", "status-customer-switch" -> pojo.setBody("[" + "\"" + param1 + "\"" + "]");
            case "update-cluster-toggle" -> updateQueryParams.apply(pojo, Map.of("customerId", param1, "cluster", param2));
            case "topaz-balance-sensedia", "account-balance" -> updateQueryParams.apply(pojo, Map.of("customerId", param1));
            case "consultar-accm-customer" -> updateQueryParams.apply(pojo, Map.of("email", param1));
            case "financial-operator" -> pojo.setEndpoint(pojo.getEndpoint() + param1);
            case "balance-institution" -> pojo.setBody(updateBody.apply(buildValues.apply(Map.of("owner_code", param1, "institution_name", param2)), pathFunc.apply(feature, "body")));
            case "criar-if" -> { pojo.setBody(updateBody.apply(buildValues.apply(Map.of("customerId",param1)), pathFunc.apply(feature, "body")));
                                 updateHeaders.apply(pojo, transactionIDHeaders.get()); }
            case "cashin-ip" -> { pojo.setBody(updateBody.apply(buildValuesCashin.apply(Map.of("account",param1,"amount",param2)), pathFunc.apply(feature, "body"))); updateHeaders.apply(pojo, transactionIDHeaders.get()); }
            case "switch-cluster-customer" -> pojo.setBody(updateBody.apply(buildValues.apply(Map.of("migrationCode", UUID.randomUUID().toString(),"customerId", param1, "origin", param2, "destiny", param3, "accountNumber", param4)), pathFunc.apply(feature, "body")));

        }
        return pojo;
    };



    Function<String, Api2POJO> buildPojoDLQ = endpoint ->
            apiConfFromPojo.apply(pathFuncMovement.apply(endpoint, "json"));

    Function<String, String> retornaUserDinamico = email -> {
        String randomDigits = IntStream.range(0, 5)
                .mapToObj(i -> String.valueOf(new Random().nextInt(10)))
                .collect(Collectors.joining(""));
        return email + randomDigits + "@mock.com";
    };

}
