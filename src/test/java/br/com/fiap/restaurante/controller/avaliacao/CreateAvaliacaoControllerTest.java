package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.repository.RestauranteRepository;
import br.com.fiap.restaurante.utils.AvaliacaoTestUtils;
import br.com.fiap.restaurante.utils.RestauranteTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste (application-test.properties)
@Transactional
class CreateAvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void finish() {
        restauranteRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
        restauranteRepository.deleteAll();
    }

    @Test
    void testCreateAvaliacao() throws Exception {
        var restaurante = restauranteRepository.save(RestauranteTestUtils.getDefaultRestaurante());

        var request = AvaliacaoTestUtils.getDefaultRequestCreateAvaliacaoDTO(restaurante.getId());

        mockMvc.perform(post("/create-avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Verifica se o status é 201 Created
                .andExpect(jsonPath("$.nomeCliente", is(request.nomeCliente())))
                .andExpect(jsonPath("$.dataCriacao").isNotEmpty())
                .andExpect(jsonPath("$.restauranteId", is(request.restauranteId().intValue())))
                .andExpect(jsonPath("$.valorAvaliacao", is(request.valorAvaliacao().toString())))
                .andExpect(jsonPath("$.comentario", is(request.comentario())));

        // Verifica se a especialidade foi salva no banco de dados
        assertThat(restauranteRepository.count()).isEqualTo(1);
    }

    @Test
    void testCreateAvaliacaoWithEmptyNomeCliente() throws Exception {
        var restaurante = restauranteRepository.save(RestauranteTestUtils.getDefaultRestaurante());

        // Create a request with an empty "nomeCliente"
        var request = AvaliacaoTestUtils.getDefaultRequestCreateAvaliacaoClienteAnonimoDTO(restaurante.getId());

        mockMvc.perform(post("/create-avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Verifica se o status é 201 Created
                .andExpect(jsonPath("$.nomeCliente", is("Anônimo"))) // Verifica se o nomeCliente foi alterado para "Anônimo"
                .andExpect(jsonPath("$.dataCriacao").isNotEmpty())
                .andExpect(jsonPath("$.restauranteId", is(request.restauranteId().intValue())))
                .andExpect(jsonPath("$.valorAvaliacao", is(request.valorAvaliacao().toString())))
                .andExpect(jsonPath("$.comentario", is(request.comentario())));

        // Verifica se o restaurante foi salvo no banco de dados
        assertThat(restauranteRepository.count()).isEqualTo(1);
    }

    @Test
    void testNotFoundRestauranteInCreateAvaliacao() throws Exception {
        var request = AvaliacaoTestUtils.getDefaultRequestCreateAvaliacaoDTO(1L);

        mockMvc.perform(post("/create-avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CreateAvaliacaoService: identificador restaurante não econtrado"));
    }

    @Test
    void testInvalidParamCreateAvaliacao() throws Exception {
        var request = AvaliacaoTestUtils.getDefaultInvalidRequestCreateAvaliacaoDTO();

        mockMvc.perform(post("/create-avaliacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.length()").value(3))
                .andExpect(
                        jsonPath("$.messages[*].campo")
                                .value(
                                        containsInAnyOrder(
                                               "valorAvaliacao",
                                                "comentario",
                                                "restauranteId"
                                        )
                                ));
    }
}