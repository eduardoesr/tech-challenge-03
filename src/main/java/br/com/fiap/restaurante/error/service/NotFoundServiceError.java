package br.com.fiap.restaurante.error.service;

public class NotFoundServiceError extends RuntimeException {
    public NotFoundServiceError(String message) {super(message);
    }
}