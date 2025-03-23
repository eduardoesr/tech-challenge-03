package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.model.Restaurante;

import java.util.Set;

public abstract class EspecialidadeService {

    public static Especialidade toEntity(EspecialidadeDTO especialidadeDTO, Set<Restaurante> restaurante) {
        return new Especialidade(
                especialidadeDTO.nome(),
                especialidadeDTO.descricao(),
                restaurante
        );
    }

    public static EspecialidadeDTO toEspecialidadeDTO(Especialidade especialidade) {
        return new EspecialidadeDTO(
                especialidade.getId(),
                especialidade.getNome(),
                especialidade.getDescricao()
        );
    }
}