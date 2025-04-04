package br.com.fiap.restaurante.service.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.service.avaliacao.AvaliacaoService;
import br.com.fiap.restaurante.service.especialidade.EspecialidadeService;
import br.com.fiap.restaurante.service.reserva.ReservaService;

import java.util.stream.Collectors;

public abstract class RestauranteService {

    protected RestauranteService () {}

    public static RestauranteDTO toRestauranteDTO(Restaurante restaurante) {
        return new RestauranteDTO(
                restaurante.getId(),
                restaurante.getAvaliacoes() == null ? null : restaurante.getAvaliacoes()
                        .stream()
                        .map(AvaliacaoService::toAvaliacaoDTO)
                        .collect(Collectors.toSet()),
                restaurante.getReservas() == null ? null : restaurante.getReservas()
                        .stream()
                        .map(ReservaService::toReservaDTO)
                        .collect(Collectors.toSet()),
                EspecialidadeService.toEspecialidadeDTO(restaurante.getEspecialidade()),
                restaurante.getCapacidadePessoas(),
                restaurante.getNome(),
                restaurante.getLatitude(),
                restaurante.getLongitude(),
                restaurante.getEnderecoCompleto(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                restaurante.getDiasFuncionamento()
        );
    }
}