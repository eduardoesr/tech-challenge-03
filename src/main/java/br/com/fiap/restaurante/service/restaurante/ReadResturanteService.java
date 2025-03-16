package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadResturanteService extends RestauranteService {

    RestauranteRepository repository;

    @Autowired
    public ReadResturanteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public RestauranteDTO find(Long id) {
        Restaurante restaurante = repository.findById(id).orElseThrow();//TODO implementar error para 'not find'
        return toRestauranteDTO(restaurante);
    }

    public List<RestauranteDTO> findAll(Pageable pageable) {
        Page<Restaurante> restaurantes = repository.findAll(pageable);
        return restaurantes.stream().map(RestauranteService::toRestauranteDTO).toList();
    }
}
