package br.com.fiap.restaurante.dto.reserva;

import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.model.context.StatusReserva;

import java.time.LocalDateTime;

public record ReservaDTO(
    Long id,
    Long restauranteId,
    String nomeCliente,
    Integer quantidadePessoas,
    LocalDateTime dataReserva,
    LocalDateTime dataSaida,
    StatusReserva statusReserva
) {}
