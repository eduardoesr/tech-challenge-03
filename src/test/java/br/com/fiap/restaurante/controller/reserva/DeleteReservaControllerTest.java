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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DeleteReservaControllerTest {

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
    void testDeleteReservaById() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);
        Long reservaId = reservaTestUtils.createReserva(restauranteId);

        mockMvc.perform(delete("/delete-reserva/{id}", reservaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // 204 No Content

        // Verify that the reservation is deleted
        mockMvc.perform(delete("/delete-reserva/{id}", reservaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404 Not Found
    }
}