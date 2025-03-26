package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestUpdateReservaDTO;
import br.com.fiap.restaurante.model.context.StatusReserva;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.utils.ReservaTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UpdateReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private ReservaTestUtils reservaTestUtils;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        reservaTestUtils = new ReservaTestUtils(mockMvc, objectMapper);
    }

    @Test
    void testUpdateReserva() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        Long reservaId = reservaTestUtils.createReserva(restauranteId);

        RequestUpdateReservaDTO updateReservaRequest = new RequestUpdateReservaDTO(
                restauranteId,
                "Novo Nome Cliente",
                4,
                reservaTestUtils.obterDataReserva(true, true),
                null,
                StatusReserva.PENDENTE
        );

        mockMvc.perform(put("/update-reserva/{id}", reservaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReservaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reservaId.intValue())))
                .andExpect(jsonPath("$.nomeCliente", is("Novo Nome Cliente")))
                .andExpect(jsonPath("$.quantidadePessoas", is(4)));
    }

    @Test
    void testUpdateReservaIniciar() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        Long reservaId = reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(put("/update-reserva/{id}/iniciar", reservaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusReserva", is(StatusReserva.EM_ANDAMENTO.toString())));
    }

    @Test
    void testUpdateReservaFinalizar() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        Long reservaId = reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(put("/update-reserva/{id}/finalizar", reservaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusReserva", is(StatusReserva.CONCLUIDO.toString())));
    }

    @Test
    void testUpdateReservaCancelar() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        Long reservaId = reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(put("/update-reserva/{id}/cancelar", reservaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusReserva", is(StatusReserva.CANCELADO.toString())));
    }

}