package br.com.fiap.restaurante.service.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.model.Especialidade;

public abstract class EspecialidadeService {

    protected EspecialidadeService() {}

    public static EspecialidadeDTO toEspecialidadeDTO(Especialidade especialidade) {
        return new EspecialidadeDTO(
                especialidade.getId(),
                especialidade.getNome(),
                especialidade.getDescricao()
        );
    }
}