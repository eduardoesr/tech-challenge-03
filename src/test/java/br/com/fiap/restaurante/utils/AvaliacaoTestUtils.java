package br.com.fiap.restaurante.utils;

import br.com.fiap.restaurante.dto.avaliacao.RequestCreateAvaliacaoDTO;
import br.com.fiap.restaurante.dto.avaliacao.RequestUpdateAvaliacaoDTO;
import br.com.fiap.restaurante.model.Avaliacao;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.model.context.ValorAvaliacao;

import java.time.LocalDateTime;

public class AvaliacaoTestUtils {
    public static Avaliacao getDefaultAvaliacao(Restaurante restaurante) {
        return new Avaliacao(
            restaurante,
            "comentario",
            "cliente",
            null,
            LocalDateTime.now(),
            ValorAvaliacao.BOM
        );
    }

    public static RequestUpdateAvaliacaoDTO getDefaultRequestUpdateAvaliacaoDTO() {
        return new RequestUpdateAvaliacaoDTO(
            "new comentario",
            ValorAvaliacao.BOM
        );
    }

    public static RequestUpdateAvaliacaoDTO getDefaultInvalidRequestUpdateAvaliacaoDTO() {
        return new RequestUpdateAvaliacaoDTO(
            "",
            null
        );
    }

    public static RequestCreateAvaliacaoDTO getDefaultRequestCreateAvaliacaoDTO(Long id) {
        return new RequestCreateAvaliacaoDTO(
            id,
            "comentario",
            "cliente",
            ValorAvaliacao.BOM
        );
    }

    public static RequestCreateAvaliacaoDTO getDefaultInvalidRequestCreateAvaliacaoDTO() {
        return new RequestCreateAvaliacaoDTO(
            null,
            "",
            null,
            null
        );
    }
}
