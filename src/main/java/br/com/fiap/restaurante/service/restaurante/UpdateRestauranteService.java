package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RequestUpdateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateRestauranteService extends RestauranteService {

    RestauranteRepository repository;
    EspecialidadeRepository especialidadeRepository;

    @Autowired
    public UpdateRestauranteService(
            RestauranteRepository repository,
            EspecialidadeRepository especialidadeRepository
    ) {
        this.repository = repository;
        this.especialidadeRepository = especialidadeRepository;
    }

    public RestauranteDTO update(Long id, RequestUpdateRestauranteDTO restauranteDTO) {
        if(!especialidadeRepository.existsById(restauranteDTO.especialidadeId())) {
            throw new NotFoundServiceError("UpdateRestaurante: identificador da especialidade não existe");
        }
        try {
            var restaurante = repository.getReferenceById(id);
            restaurante.setEspecialidade(especialidadeRepository.getReferenceById(restauranteDTO.especialidadeId()));
            restaurante.setCapacidadePessoas(restauranteDTO.capacidadePessoas());
            restaurante.setNome(restauranteDTO.nome());
            restaurante.setLatitude(restauranteDTO.latitude());
            restaurante.setLongitude(restauranteDTO.longitude());
            restaurante.setEnderecoCompleto(restauranteDTO.enderecoCompleto());
            restaurante.setHorarioAbertura(restauranteDTO.horarioAbertura());
            restaurante.setHorarioFechamento(restauranteDTO.horarioFechamento());
            restaurante.setDiasFuncionamento(restauranteDTO.diasFuncionamentos());
            restaurante.setTolerancia(restauranteDTO.tolerancia());
            return toRestauranteDTO(repository.save(restaurante));
        } catch (EntityNotFoundException e) {
            throw new NotFoundServiceError("UpdateRestaurante: identificador do restaurante não encontrado");
        }
    }
}
