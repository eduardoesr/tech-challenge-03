package br.com.fiap.restaurante.dto.restaurante;

import br.com.fiap.restaurante.model.context.DiasFuncionamento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Set;

public record RequestCreateRestauranteDTO (
        @NotNull
        Long especialidadeId,
        @NotNull
        @Min(1)
        Integer capacidadePessoas,
        @NotBlank(message = "o nome do restaurante não pode ser vazio")
        String nome,
        @NotNull
        Integer latitude,
        @NotNull
        Integer longitude,
        @NotBlank
        String enderecoCompleto,
        @NotNull
        LocalTime horarioAbertura,
        @NotNull
        LocalTime horarioFechamento,
        @NotNull
        Set<DiasFuncionamento> diasFuncionamentos
){
}
