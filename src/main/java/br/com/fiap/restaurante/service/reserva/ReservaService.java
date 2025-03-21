package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.model.Reserva;

public abstract class ReservaService {

    public static Reserva toEntity(ReservaDTO reservaDTO) {
        return new Reserva(
                reservaDTO.nomeCliente(),
                reservaDTO.quantidadePessoas(),
                reservaDTO.dataReserva(),
                reservaDTO.dataSaida(),
                reservaDTO.statusReserva()
        );
    }

    public static ReservaDTO toReservaDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getId(),
                reserva.getRestaurante().getId(),
                reserva.getNomeCliente(),
                reserva.getQuantidadePessoas(),
                reserva.getDataReserva(),
                reserva.getDataSaida(),
                reserva.getStatusReserva()
        );
    }
}
