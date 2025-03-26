package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestCreateReservaDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CreateReservaControllerTest {

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
    void testCreateReserva() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);

        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                2,
                reservaTestUtils.obterDataReserva(true, true)
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.nomeCliente", is("Nome cliente")))
                .andExpect(jsonPath("$.quantidadePessoas", is(2)));
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void testCreateReservaRestauranteNaoEncontrado() throws Exception {
        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                999L, // Id não existente
                "Nome cliente",
                2,
                reservaTestUtils.obterDataReserva(true, true)
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("CreateReserva: Identificador do restaurante não encontrado.")));
    }

    @Test
    void testCreateReservaRestauranteFechado() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);

        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                2,
                reservaTestUtils.obterDataReserva(false, true) // Restaurante não abre no dia.
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Debugging step
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("CreateReserva: O restaurante não está aberto neste dia.")));
    }

    @Test
    void testCreateReservaAcimaDaCapacidade() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);

        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                20, // Acima da capacidade
                reservaTestUtils.obterDataReserva(true, true)
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("CreateReserva: Acima da capacidade.")));
    }

    @Test
    void testCreateReservaAntesDeAbrir() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);

        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                2,
                reservaTestUtils.obterDataReserva(true, false) // Antes de abrir
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("CreateReserva: A reserva deve ser em horário de serviço do restaurante.")));
    }

    @Test
    void testCreateReservaDepoisDeFechar() throws Exception {
        Long especialidadeId = reservaTestUtils.createEspecialidade();
        Long restauranteId = reservaTestUtils.createRestaurante(especialidadeId);

        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                2,
                reservaTestUtils.obterDataReserva(true, false, true) // Antes de abrir
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("CreateReserva: A reserva deve ser em horário de serviço do restaurante.")));
    }
}