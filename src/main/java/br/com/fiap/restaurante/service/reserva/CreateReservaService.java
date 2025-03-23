package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestCreateReservaDTO;
import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Reserva;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CreateReservaService extends ReservaService {

    ReservaRepository reservaRepository;
    RestauranteRepository restauranteRepository;

    @Autowired
    public CreateReservaService(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public ReservaDTO create(RequestCreateReservaDTO reservaDTO) {
        // TODO: Corrigir erros.
        Restaurante restaurante = restauranteRepository.findById(reservaDTO.restauranteId())
                .orElseThrow(() -> new NotFoundServiceError("CreateReserva: Identificador do restaurante não encontrado."));

        int totalPessoas = restaurante.getReservas().stream()
                .filter(reserva -> reserva.getDataReserva().toLocalDate().equals(reservaDTO.dataReserva().toLocalDate()))
                .mapToInt(Reserva::getQuantidadePessoas)
                .sum() + reservaDTO.quantidadePessoas();
        if (totalPessoas > restaurante.getCapacidadePessoas()) {
            throw new NotFoundServiceError("CreateReserva: Acima da capacidade.");
        }

        if (reservaDTO.dataReserva().toLocalTime().isBefore(restaurante.getHorarioAbertura())
                || reservaDTO.dataReserva().toLocalTime().isAfter(restaurante.getHorarioFechamento())) {
            throw new NotFoundServiceError("CreateReserva: A reserva deve ser em horário de serviço do restaurante.");
        }

        Duration tempoExcedido = Duration.between(reservaDTO.dataReserva(), LocalDateTime.now());
        if (tempoExcedido.toMinutes() > restaurante.getTolerancia().toSecondOfDay() / 60) {
            throw new NotFoundServiceError("CreateReserva: O tempo de tolerância para a reserva foi excedido.");
        }

        Reserva reserva = toEntity(reservaDTO);
        reserva.setRestaurante(restaurante);
        return toReservaDTO(reservaRepository.save(reserva));
    }
}
