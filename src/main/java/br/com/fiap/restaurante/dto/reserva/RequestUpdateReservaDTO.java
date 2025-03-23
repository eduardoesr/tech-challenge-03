package br.com.fiap.restaurante.dto.reserva;

import br.com.fiap.restaurante.model.context.StatusReserva;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestUpdateReservaDTO(
    @Min(value=1, message="O id do restaurante deve ser acima de zero.")
    Long restauranteId,
    @NotBlank(message = "Especifique o cliente responsável pela reserva.")
    String nomeCliente,
    @Min(value=1, message="A reserva deve ser para ao menos uma pessoa.")
    Integer quantidadePessoas,
    @NotNull(message = "Especifique a data da reserva.")
    LocalDateTime dataReserva,
    LocalDateTime dataSaida,
    @NotNull(message = "Especifique o status da reserva.")
    StatusReserva statusReserva
) {}