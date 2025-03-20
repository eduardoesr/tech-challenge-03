package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteEspecialidadeService extends EspecialidadeService {

    EspecialidadeRepository repository;

    @Autowired
    public DeleteEspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public void deleteById(Long id) {
        if(!repository.existsById(id)){
            throw new NotFoundServiceError("DeleteEspecialidade: identificador não foi encontrado");
        }
        repository.deleteById(id);
    }
}