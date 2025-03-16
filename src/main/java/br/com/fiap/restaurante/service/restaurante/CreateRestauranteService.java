package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRestauranteService extends RestauranteService {

    RestauranteRepository repository;

    @Autowired
    public CreateRestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public RestauranteDTO create(RestauranteDTO dto) {
        Restaurante restaurante = toEntity(dto);
        return toRestauranteDTO(repository.save(restaurante));
    }
}
