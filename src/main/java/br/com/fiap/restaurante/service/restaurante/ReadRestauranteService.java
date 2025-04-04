package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReadRestauranteService extends RestauranteService {

    RestauranteRepository repository;

    @Autowired
    public ReadRestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public RestauranteDTO findById(Long id) {
        Restaurante restaurante = repository.findById(id).orElseThrow(
                () -> new NotFoundServiceError("ReadRestaurante: identificador não encontrado"));
        return toRestauranteDTO(restaurante);
    }

    public Page<RestauranteDTO> findByNomeContainingIgnoreCase(String nome, Pageable pageable) {
        Page<Restaurante> restaurantes = repository.findByNomeContainingIgnoreCase(nome, pageable);
        return restaurantes.map(RestauranteService::toRestauranteDTO);
    }

    public Page<RestauranteDTO> findAll(Pageable pageable) {
        Page<Restaurante> restaurantes = repository.findAll(pageable);
        return restaurantes.map(RestauranteService::toRestauranteDTO);
    }
}
