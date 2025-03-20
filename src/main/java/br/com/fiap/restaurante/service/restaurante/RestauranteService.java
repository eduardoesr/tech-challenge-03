package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.service.especialidade.EspecialidadeService;

public abstract class RestauranteService {

    public static Restaurante toEntity(RestauranteDTO restauranteDTO) {
        return new Restaurante(
                restauranteDTO.avaliacoes(),
                restauranteDTO.reservas(),
                EspecialidadeService.toEntity(restauranteDTO.especialidade()),
                restauranteDTO.capacidadePessoas(),
                restauranteDTO.nome(),
                restauranteDTO.latitude(),
                restauranteDTO.longitude(),
                restauranteDTO.enderecoCompleto(),
                restauranteDTO.horarioAbertura(),
                restauranteDTO.horarioFechamento(),
                restauranteDTO.tolerancia(),
                restauranteDTO.diasFuncionamentos()
        );
    }

    public static RestauranteDTO toRestauranteDTO(Restaurante restaurante) {
        return new RestauranteDTO(
                restaurante.getId(),
                restaurante.getAvaliacoes(),
                restaurante.getReservas(),
                EspecialidadeService.toEspecialidadeDTO(restaurante.getEspecialidade()),
                restaurante.getCapacidadePessoas(),
                restaurante.getNome(),
                restaurante.getLatitude(),
                restaurante.getLongitude(),
                restaurante.getEnderecoCompleto(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasTolerancia(),
                restaurante.getTolerancia()
        );
    }
}