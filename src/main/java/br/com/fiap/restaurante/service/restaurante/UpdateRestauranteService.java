package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateRestauranteService extends RestauranteService {

    RestauranteRepository repository;

    @Autowired
    public UpdateRestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public RestauranteDTO update(Long id, RestauranteDTO restauranteDTO) {
        try {
            var restaurante = repository.getReferenceById(id);
            restaurante.setAvaliacoes(restauranteDTO.avaliacoes());
            restaurante.setReservas(restauranteDTO.reservas());
            restaurante.setEspecialidade(restauranteDTO.especialidade());
            restaurante.setCapacidadePessoas(restauranteDTO.capacidadePessoas());
            restaurante.setNome(restauranteDTO.nome());
            restaurante.setLatitude(restauranteDTO.latitude());
            restaurante.setLongitude(restauranteDTO.longitude());
            restaurante.setEnderecoCompleto(restauranteDTO.enderecoCompleto());
            restaurante.setHorarioAbertura(restauranteDTO.horarioAbertura());
            restaurante.setHorarioFechamento(restauranteDTO.horarioFechamento());
            restaurante.setDiasTolerancia(restauranteDTO.diasFuncionamentos());
            restaurante.setTolerancia(restauranteDTO.tolerancia());
            return toRestauranteDTO(repository.save(restaurante));
        } catch (EntityNotFoundException e) {
            //TODO implementar erros personalizados de EntityNotFoundException
            throw new RuntimeException();
        }
    }
}
