package br.com.fiap.restaurante.dto.avaliacao;

import br.com.fiap.restaurante.model.context.ValorAvaliacao;

import java.time.LocalDateTime;

public record AvaliacaoDTO(
    Long id,
    Long restauranteId,
    String comentario,
    String nomeCliente,
    LocalDateTime dataCriacao,
    LocalDateTime dataUpdate,
    ValorAvaliacao valorAvaliacao
) {}
