package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRestauranteService extends RestauranteService {

    RestauranteRepository repository;
    EspecialidadeRepository repositoryEspecialidade;

    @Autowired
    public CreateRestauranteService(
            RestauranteRepository repository,
            EspecialidadeRepository repositoryEspecialidade
    ) {
        this.repository = repository;
        this.repositoryEspecialidade = repositoryEspecialidade;
    }

    public RestauranteDTO create(RequestCreateRestauranteDTO dto) {
        if(!repositoryEspecialidade.existsById(dto.especialidadeId())) {
            throw new NotFoundServiceError("CreateRestaurante: identificador da especialidade não encontrado");
        }

        Restaurante restaurante = toEntity(dto);
        restaurante.setEspecialidade(repositoryEspecialidade.getReferenceById(dto.especialidadeId()));

        return toRestauranteDTO(repository.save(restaurante));
    }

    public static Restaurante toEntity(
            RequestCreateRestauranteDTO restauranteDTO
    ) {
        return new Restaurante(
                restauranteDTO.capacidadePessoas(),
                restauranteDTO.nome(),
                restauranteDTO.latitude(),
                restauranteDTO.longitude(),
                restauranteDTO.enderecoCompleto(),
                restauranteDTO.horarioAbertura(),
                restauranteDTO.horarioFechamento(),
                restauranteDTO.diasFuncionamentos()
        );
    }
}
