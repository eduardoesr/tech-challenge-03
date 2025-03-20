package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadResturanteService extends EspecialidadeService {

    EspecialidadeRepository repository;

    @Autowired
    public ReadResturanteService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public RestauranteDTO findById(Long id) {
        Restaurante restaurante = repository.findById(id).orElseThrow(() -> new NotFoundServiceError("ReadResturante: identificador não encontrado"));//TODO implementar error para 'not find'
        return toEspecialidadeDTO(restaurante);
    }

    public List<RestauranteDTO> findAll(Pageable pageable) {
        Page<Restaurante> restaurantes = repository.findAll(pageable);
        return restaurantes.stream().map(EspecialidadeService::toEspecialidadeDTO).toList();
    }
}
