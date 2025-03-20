package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadEspecialidadeService extends EspecialidadeService {

    EspecialidadeRepository repository;

    @Autowired
    public ReadEspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public EspecialidadeDTO findById(Long id) {
        Especialidade especialidade = repository.findById(id).orElseThrow(() -> new NotFoundServiceError("ReadEspecialidade: identificador não encontrado"));//TODO implementar error para 'not find'
        return toEspecialidadeDTO(especialidade);
    }

    public Page<EspecialidadeDTO> findAll(Pageable pageable) {
        Page<Especialidade> especialidades = repository.findAll(pageable);
        return especialidades.map(EspecialidadeService::toEspecialidadeDTO);
    }
}
