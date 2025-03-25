package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestUpdateReservaDTO;
import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Reserva;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.model.context.StatusReserva;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateReservaService extends ReservaService {

    ReservaRepository reservaRepository;
    RestauranteRepository restauranteRepository;

    @Autowired
    public UpdateReservaService(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public ReservaDTO update(Long id, RequestUpdateReservaDTO reservaDTO) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceError("UpdateReserva: Reserva não encontrada."));
        Restaurante restaurante = restauranteRepository.findById(reservaDTO.restauranteId())
                .orElseThrow(() -> new NotFoundServiceError("CreateReserva: Identificador do restaurante não encontrado."));
        reserva.setRestaurante(restaurante);
        reserva.setNomeCliente(reservaDTO.nomeCliente());
        reserva.setQuantidadePessoas(reservaDTO.quantidadePessoas());
        reserva.setDataReserva(reservaDTO.dataReserva());
        reserva.setDataSaida(reservaDTO.dataSaida());
        reserva.setStatusReserva(reservaDTO.statusReserva());
        return toReservaDTO(reservaRepository.save(reserva));
    }

    public ReservaDTO updateSimples(Long id, StatusReserva statusReserva) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundServiceError("UpdateReserva: Reserva não encontrada."));
        reserva.setStatusReserva(statusReserva);
        if (statusReserva == StatusReserva.CONCLUIDO) {
            reserva.setDataSaida(LocalDateTime.now());
        }
        return toReservaDTO(reservaRepository.save(reserva));
    }
}
