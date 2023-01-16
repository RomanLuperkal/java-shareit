package ru.practicum.shareit.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BaseWebClient {
    protected WebClient webClient;

    public BaseWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    protected Mono<ResponseEntity<Object>> get(String path) {
        return get(path, null, null);
    }

    protected Mono<ResponseEntity<Object>> get(String path, long userId) {
        return get(path, userId, null);
    }

    protected Mono<ResponseEntity<Object>> get(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, parameters, null);
    }

    protected <T> Mono<ResponseEntity<Object>> post(String path, T body) {
        return post(path, null, null, body);
    }

    protected <T> Mono<ResponseEntity<Object>> post(String path, long userId, T body) {
        return post(path, userId, null, body);
    }

    protected <T> Mono<ResponseEntity<Object>> post(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, parameters, body);
    }

    protected <T> Mono<ResponseEntity<Object>> patch(String path, T body) {
        return patch(path, null, null, body);
    }


    protected <T> Mono<ResponseEntity<Object>> patch(String path, long userId, T body) {
        return patch(path, userId, null, body);
    }

    protected <T> Mono<ResponseEntity<Object>> patch(String path, Long userId, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, parameters, body);
    }

    protected Mono<ResponseEntity<Object>> delete(String path) {
        return delete(path, null, null);
    }

    protected Mono<ResponseEntity<Object>> delete(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.DELETE, path, userId, parameters, null);
    }

    private <T> Mono<ResponseEntity<Object>> makeAndSendRequest(HttpMethod method, String path, Long userId,
                                                                @Nullable Map<String, Object> parameters, @Nullable T body) {
        if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PATCH)) {
            if (parameters == null) {
                Mono<Object> monoBody = Mono.just(body);
                return webClient
                        .method(method)
                        .uri(path)
                        .headers(defaultHeaders(userId))
                        .body(monoBody, Object.class)
                        .retrieve()
                        .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                        .toEntity(Object.class)
                        .timeout(Duration.ofMinutes(1));
            } else {
                return webClient
                        .method(method)
                        .uri(path, parameters)
                        .headers(defaultHeaders(userId))
                        .retrieve()
                        .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                        .toEntity(Object.class)
                        .timeout(Duration.ofMinutes(1));
            }
        }
        if (parameters == null) {
            return webClient
                    .method(method)
                    .uri(path)
                    .headers(defaultHeaders(userId))
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                    .toEntity(Object.class)
                    .timeout(Duration.ofMinutes(1));
        } else {
            return webClient
                    .method(method)
                    .uri(path, parameters)
                    .headers(defaultHeaders(userId))
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                            .flatMap(error -> Mono.error(new ResponseStatusException(response.statusCode(), error))))
                    .toEntity(Object.class)
                    .timeout(Duration.ofMinutes(1));
        }
    }

    private Consumer<HttpHeaders> defaultHeaders(Long userId) {
        return httpHeaders -> {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
            if (userId != null) {
                httpHeaders.set("X-Sharer-User-Id", String.valueOf(userId));
            }
        };
    }
}
