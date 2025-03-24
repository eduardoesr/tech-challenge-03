package br.com.fiap.restaurante.dto.reserva;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestCreateReservaDTO(
    @Min(value=0, message="O id do restaurante deve ser positivo.")
    Long restauranteId,
    @NotBlank(message = "Especifique o cliente responsável pela reserva.")
    String nomeCliente,
    @Min(value=1, message="A reserva deve ser para ao menos uma pessoa.")
    Integer quantidadePessoas,
    @NotNull(message = "Especifique a data da reserva.")
    LocalDateTime dataReserva
) {}
