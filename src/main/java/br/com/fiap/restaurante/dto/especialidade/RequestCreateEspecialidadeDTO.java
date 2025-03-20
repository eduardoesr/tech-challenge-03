package br.com.fiap.restaurante.dto.especialidade;

import jakarta.validation.constraints.NotBlank;

public record RequestCreateRestauranteDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao
){
}
