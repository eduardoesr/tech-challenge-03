package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.model.Reserva;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateReservaService extends ReservaService {

    ReservaRepository reservaRepository;
    RestauranteRepository restauranteRepository;

    @Autowired
    public CreateReservaService(ReservaRepository reservaRepository, RestauranteRepository restauranteRepository) {
        this.reservaRepository = reservaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public ReservaDTO create(ReservaDTO reservaDTO) {
        Restaurante restaurante = restauranteRepository.findById(reservaDTO.restauranteId()).orElseThrow(); //TODO implementar error para 'not find'
        Reserva reserva = toEntity(reservaDTO);
        reserva.setRestaurante(restaurante);
        return toReservaDTO(reservaRepository.save(reserva));
    }

}
