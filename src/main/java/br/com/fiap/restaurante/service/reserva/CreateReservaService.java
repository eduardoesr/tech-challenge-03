package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestCreateReservaDTO;
import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Reserva;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.model.context.DiasFuncionamento;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

@Service
public class CreateReservaService extends ReservaService {

    ReservaRepository reservaRepository;
    RestauranteRepository restauranteRepository;

    private static final Map<DayOfWeek, DiasFuncionamento> dayOfWeekToDiasFuncionamentoMap = new EnumMap<>(DayOfWeek.class);
    static {
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.MONDAY, DiasFuncionamento.SEG);
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.TUESDAY, DiasFuncionamento.TER);
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.WEDNESDAY, DiasFuncionamento.QUA);
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.THURSDAY, DiasFuncionamento.QUI);
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.FRIDAY, DiasFuncionamento.SEX);
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.SATURDAY, DiasFuncionamento.SAB);
        dayOfWeekToDiasFuncionamentoMap.put(DayOfWeek.SUNDAY, DiasFuncionamento.DOM);
    }

    @Autowired
    public CreateReservaService(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public ReservaDTO create(RequestCreateReservaDTO reservaDTO) {
        // TODO: Corrigir erros.
        Restaurante restaurante = restauranteRepository.findById(reservaDTO.restauranteId())
                .orElseThrow(() -> new NotFoundServiceError("CreateReserva: Identificador do restaurante não encontrado."));

        DayOfWeek diaAtual = reservaDTO.dataReserva().getDayOfWeek();
        if (!restaurante.getDiasFuncionamento().contains(dayOfWeekToDiasFuncionamentoMap.get(diaAtual))) {
            throw new NotFoundServiceError("CreateReserva: O restaurante não está aberto neste dia.");
        }

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
