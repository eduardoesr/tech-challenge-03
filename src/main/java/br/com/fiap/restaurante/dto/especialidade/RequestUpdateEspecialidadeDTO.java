package br.com.fiap.restaurante.dto.especialidade;

import jakarta.validation.constraints.NotBlank;

public record RequestUpdateEspecialidadeDTO (
        @NotBlank
        String nome,
        @NotBlank
        String descricao
){
}
