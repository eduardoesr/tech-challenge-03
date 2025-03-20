package br.com.fiap.restaurante.dto.restaurante;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.model.Avaliacao;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.model.Reserva;
import br.com.fiap.restaurante.model.context.DiasFuncionamento;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Set;

public record RestauranteDTO(
        Long id,
        Set<Avaliacao> avaliacoes,
        Set<Reserva> reservas,
        EspecialidadeDTO especialidade,
        Integer capacidadePessoas,
        String nome,
        Integer latitude,
        Integer longitude,
        String enderecoCompleto,
        LocalTime horarioAbertura,
        LocalTime horarioFechamento,
        Set<DiasFuncionamento> diasFuncionamentos,
        LocalTime tolerancia
) {
}
