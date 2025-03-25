package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateEspecialidadeService extends EspecialidadeService {

    EspecialidadeRepository repository;

    @Autowired
    public UpdateEspecialidadeService(
            EspecialidadeRepository repository
    ) {
        this.repository = repository;
    }

    public EspecialidadeDTO update(Long id, RequestUpdateEspecialidadeDTO especialidadeDTO) {
        try {
            var especialidade = repository.getReferenceById(id);
            especialidade.setNome(especialidadeDTO.nome());
            especialidade.setDescricao(especialidadeDTO.descricao());
            return toEspecialidadeDTO(repository.save(especialidade));
        } catch (EntityNotFoundException e) {
            throw new NotFoundServiceError("UpdateEspecialidade: identificador da especialidade não encontrada");
        }
    }
}
