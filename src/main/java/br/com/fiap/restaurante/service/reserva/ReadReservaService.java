package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Reserva;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReadReservaService extends ReservaService {

    ReservaRepository reservaRepository;
    RestauranteRepository restauranteRepository;

    @Autowired
    public ReadReservaService(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public List<ReservaDTO> findAll(Pageable pageable) {
        Page<Reserva> reservas = reservaRepository.findAll(pageable);
        return reservas.stream().map(ReservaService::toReservaDTO).toList();
    }

    public ReservaDTO findById(Long id) {
        Reserva reservas = reservaRepository.findById(id).orElseThrow(() -> new NotFoundServiceError("ReadResturante: identificador reserva não encontrado "));
        return toReservaDTO(reservas);
    }

    public List<ReservaDTO> findAllByRestauranteId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new NotFoundServiceError("ReadResturante: identificador restaurante não encontrado"));
        Set<Reserva> reservas = restaurante.getReservas();
        return reservas.stream().map(ReservaService::toReservaDTO).toList();
    }
}
