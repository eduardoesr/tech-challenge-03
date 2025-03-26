package br.com.fiap.restaurante.controller.reserva;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReadReservaControllerTest {

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
    void testGetReservaById() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        Long reservaId = reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(get("/reserva/{id}", reservaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reservaId.intValue())))
                .andExpect(jsonPath("$.nomeCliente", is("Nome cliente")))
                .andExpect(jsonPath("$.quantidadePessoas", is(2)));
    }

    @Test
    void testGetReservaByIdNotFound() throws Exception {
        mockMvc.perform(get("/reserva/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ReadReserva: Reserva não encontrada")));
    }

    @Test
    void testGetAllReservas() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        reservaTestUtils.createReserva(restauranteId);
        reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(get("/reserva")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void testGetAllReservasRestaurante() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        reservaTestUtils.createReserva(restauranteId);
        reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(get("/reserva/restaurante/{id}", restauranteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void testGetAllReservasRestauranteIdNotFound() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        reservaTestUtils.createReserva(restauranteId);
        reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(get("/reserva/restaurante/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ReadReserva: Restaurante não encontrado")));
    }
}