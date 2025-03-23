package br.com.fiap.restaurante.dto.avaliacao;

import br.com.fiap.restaurante.model.context.ValorAvaliacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestUpdateAvaliacaoDTO(
        @NotBlank
        String comentario,
        @NotNull
        ValorAvaliacao valorAvaliacao
) {
}
