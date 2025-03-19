package br.com.fiap.restaurante.exception.service;

public class NotFoundService extends RuntimeException {
    public NotFoundService(String message) {super(message);
    }
}
