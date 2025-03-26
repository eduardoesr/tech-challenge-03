package br.com.fiap.restaurante.dto.restaurante;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.model.context.DiasFuncionamento;

import java.time.LocalTime;
import java.util.Set;

public record RestauranteDTO(
        Long id,
        Set<AvaliacaoDTO> avaliacoes,
        Set<ReservaDTO> reservas,
        EspecialidadeDTO especialidade,
        Integer capacidadePessoas,
        String nome,
        Integer latitude,
        Integer longitude,
        String enderecoCompleto,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        Set<DiasFuncionamento> diasFuncionamentos
) {
}
