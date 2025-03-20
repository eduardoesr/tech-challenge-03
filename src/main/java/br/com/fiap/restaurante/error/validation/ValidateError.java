package br.com.fiap.restaurante.error.validation;

import br.com.fiap.restaurante.error.StandardError;

import java.util.ArrayList;
import java.util.List;

public class ValidateError extends StandardError {
    private List<ValidationMessage> messages = new ArrayList<>();

    public List<ValidationMessage> getMessages() {
        return messages;
    }

    public void addMessages(String campo, String message) {
        this.messages.add(new ValidationMessage(campo, message));
    }
}
