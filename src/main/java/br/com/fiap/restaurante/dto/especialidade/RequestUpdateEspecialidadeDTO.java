package br.com.fiap.restaurante.dto.especialidade;

import br.com.fiap.restaurante.model.context.DiasFuncionamento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Set;

public record RequestUpdateEspecialidadeDTO (
        @NotBlank
        String nome,
        @NotBlank
        String descricao
){
}
