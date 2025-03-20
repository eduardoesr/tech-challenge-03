package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteRestauranteService extends RestauranteService {

    RestauranteRepository repository;

    @Autowired
    public DeleteRestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public void deleteById(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return;
        }
        throw new NotFoundServiceError("DeleteRestaurante: identificador não foi encontrado");
    }
}