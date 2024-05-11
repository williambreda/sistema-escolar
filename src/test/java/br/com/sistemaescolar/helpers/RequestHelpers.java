package br.com.sistemaescolar.helpers;

import br.com.sistemaescolar.data.Api2POJO;
import io.vavr.control.Try;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


public interface RequestHelpers {

    Supplier<TrustManager[]> trustAllCerts = () -> new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(
                        X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        X509Certificate[] certs, String authType) {
                }
            }
    };

    Function<TrustManager[], SSLContext> sslContext = tm -> {
        SSLContext sc = Try.of(() -> SSLContext.getInstance("SSL")).get();
        Try.run(() -> sc.init(null, tm, new java.security.SecureRandom())).get();
        return sc;
    };

    BiFunction<Supplier<TrustManager[]>, Function<TrustManager[], SSLContext>, HttpClient> updateClientSpecs = (trustManager, ssl) ->
            HttpClient.newBuilder()
                    .sslContext(ssl.apply(trustManager.get()))
                    .build();

    Function<Api2POJO, CompletableFuture<HttpResponse<String>>> asyncRequest = baseRequest -> {
        var client = updateClientSpecs.apply(trustAllCerts, sslContext);
        System.out.println(baseRequest.getURI());
        var getRequestMethod = switch (baseRequest.getRequestMethod().toUpperCase(Locale.ROOT)) {

            case "POST" ->
                    HttpRequest.newBuilder().uri(baseRequest.getURI()).timeout(Duration.ofSeconds(5L)).headers(baseRequest.getHeaders()).POST(HttpRequest.BodyPublishers.ofString(baseRequest.getBody())).build();
            case "PUT" ->
                    HttpRequest.newBuilder().uri(baseRequest.getURI()).timeout(Duration.ofSeconds(5L)).headers(baseRequest.getHeaders()).PUT(HttpRequest.BodyPublishers.ofString(baseRequest.getBody())).build();
            case "DELETE" ->
                    HttpRequest.newBuilder().uri(baseRequest.getURI()).timeout(Duration.ofSeconds(5L)).headers(baseRequest.getHeaders()).method("DELETE", HttpRequest.BodyPublishers.ofString(baseRequest.getBody())).build();
            case "PATCH" ->
                    HttpRequest.newBuilder().uri(baseRequest.getURI()).timeout(Duration.ofSeconds(5L)).headers(baseRequest.getHeaders()).method("PATCH", HttpRequest.BodyPublishers.ofString(baseRequest.getBody())).build();
            default ->
                    HttpRequest.newBuilder().uri(baseRequest.getURI()).timeout(Duration.ofSeconds(5L)).headers(baseRequest.getHeaders()).method("GET", HttpRequest.BodyPublishers.ofString(baseRequest.getBody())).build();

        };

        return client.sendAsync(getRequestMethod, HttpResponse.BodyHandlers.ofString());
    };


}