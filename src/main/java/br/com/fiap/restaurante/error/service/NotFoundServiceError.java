package br.com.fiap.restaurante.error.service;

public class NotFoundService extends RuntimeException {
    public NotFoundService(String message) {super(message);
    }
}
