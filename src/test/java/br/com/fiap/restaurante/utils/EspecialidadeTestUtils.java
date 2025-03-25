package br.com.fiap.restaurante.utils;

import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.model.Especialidade;

import java.time.LocalDateTime;
import java.util.Set;

public class EspecialidadeTestUtils {

    public static Especialidade getDefaultEspecialidade() {
        return new Especialidade(
                "test - " + LocalDateTime.now(),
                "uso nos test",
                Set.of()
        );
    }

    public static RequestCreateEspecialidadeDTO getDefaultRequestCreateEspecialidadeDTO() {
        return new RequestCreateEspecialidadeDTO(
                "Pizza",
                "Pizza de Calabresa"
        );
    }

    public static RequestCreateEspecialidadeDTO getDefaultInvalidRequestCreateEspecialidadeDTO() {
        return new RequestCreateEspecialidadeDTO(
                "",
                ""
        );
    }

    public static RequestUpdateEspecialidadeDTO getDefaultRequestUpdateEspecialidadeDTO() {
        return new RequestUpdateEspecialidadeDTO(
                "new name",
                "new descricao"
        );
    }

    public static RequestUpdateEspecialidadeDTO getDefaultInvalidRequestUpdateEspecialidadeDTO() {
        return new RequestUpdateEspecialidadeDTO(
                "",
                ""
        );
    }


}
