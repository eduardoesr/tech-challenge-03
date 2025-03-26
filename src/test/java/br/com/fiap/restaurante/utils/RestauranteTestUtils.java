package br.com.fiap.restaurante.utils;

import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestUpdateRestauranteDTO;
import br.com.fiap.restaurante.model.Restaurante;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class RestauranteTestUtils {

    public static Restaurante getDefaultRestaurante() {
        return new Restaurante(
                10,
                "restaurante test - " + LocalDateTime.now(),
                0,
                0,
                "endereco",
                LocalTime.now(),
                LocalTime.now(),
                Set.of()
        );
    }

    public static RequestCreateRestauranteDTO getDefaultInvalidRequestCreateRestauranteDTO() {
        return new RequestCreateRestauranteDTO(
                null,
                0,
                "",
                null,
                null,
                "",
                null,
                null,
                null
        );
    }

    public static RequestUpdateRestauranteDTO getDefaultInvalidRequestUpdateRestauranteDTO() {
        return new RequestUpdateRestauranteDTO(
                null,
                0,
                "",
                null,
                null,
                "",
                null,
                null,
                null
        );
    }

    public static RequestCreateRestauranteDTO getDefaultRequestCreateRestauranteDTO(Long especialidadeId) {
        return new RequestCreateRestauranteDTO(
                especialidadeId,
                10,
                "restaurante test",
                0,
                0,
                "endereco",
                LocalTime.now(),
                LocalTime.now(),
                Set.of()
        );
    }

    public static RequestUpdateRestauranteDTO getDefaultRequestUpdateRestauranteDTO(Long especialidadeId) {
        return new RequestUpdateRestauranteDTO(
                especialidadeId,
                10,
                "restaurante test",
                0,
                0,
                "endereco",
                LocalTime.now(),
                LocalTime.now(),
                Set.of()
        );
    }
}
