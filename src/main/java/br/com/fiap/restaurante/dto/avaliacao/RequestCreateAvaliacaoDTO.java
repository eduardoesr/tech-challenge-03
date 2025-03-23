package br.com.fiap.restaurante.dto.avaliacao;

import br.com.fiap.restaurante.model.context.ValorAvaliacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestCreateAvaliacaoDTO(
        @NotNull
        Long restauranteId,
        @NotBlank
        String comentario,
        String nomeCliente,
        @NotNull
        ValorAvaliacao valorAvaliacao
) {
}
