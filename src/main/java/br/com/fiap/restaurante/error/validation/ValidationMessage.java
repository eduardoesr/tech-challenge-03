package br.com.fiap.restaurante.error.validation;

public class ValidationMessage {
    private String campo;
    private String messagem;

    public ValidationMessage(String campo, String messagem) {
        this.campo = campo;
        this.messagem = messagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMessagem() {
        return messagem;
    }
}
