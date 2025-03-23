package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEspecialidadeService extends EspecialidadeService {

    EspecialidadeRepository repository;

    @Autowired
    public CreateEspecialidadeService(
            EspecialidadeRepository repository
    ) {
        this.repository = repository;
    }

    public EspecialidadeDTO create(RequestCreateEspecialidadeDTO dto) {

        Especialidade especialidade = toEntity(dto);

        return toEspecialidadeDTO(repository.save(especialidade));
    }

    public static Especialidade toEntity(
            RequestCreateEspecialidadeDTO especialidadeDTO
    ) {
        return new Especialidade(
                especialidadeDTO.descricao(),
                especialidadeDTO.nome(),
                null
        );
    }
}
