package br.com.sistemaescolar.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public interface TestUtils {

    BiFunction<String, Class, Object> responseBodyToClass = (body, clazz) ->
            Try.of(() ->
                    (new ObjectMapper()).readValue(body, clazz)
            ).get();


    BiFunction<String, Map<String, String>, String> setPathParameters = (endpoint, pars) -> {
        AtomicReference<String> pathParameter = new AtomicReference(endpoint);
        pars.keySet().forEach((k) -> {
            pathParameter.lazySet(((String) pathParameter.get()).replaceFirst("\\{".concat(k).concat("\\}"), pars.get(k).toString()));
        });
        return (String) pathParameter.get();
    };

    BiFunction<String, Map<String, String>, String> queryParametersComposition = (url, queryParams) -> {
        AtomicReference<StringBuilder> composition = new AtomicReference(new StringBuilder(url.concat("?")));
        Iterator<Map.Entry<String, String>> iter = queryParams.entrySet().iterator();
        iter.forEachRemaining((i) -> {
            ((StringBuilder) composition.get()).append((String) i.getKey()).append("=").append(i.getValue());
            if (iter.hasNext()) {
                ((StringBuilder) composition.get()).append("&");
            }

        });
        return ((StringBuilder) composition.get()).toString();
    };



}
