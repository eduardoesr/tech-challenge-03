package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.model.Restaurante;

public class EspecialidadeService {

    public static Especialidade toEntity(EspecialidadeDTO especialidadeDTO) {
        return new Especialidade(
                especialidadeDTO.nome(),
                especialidadeDTO.descricao(),
                null
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