package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import br.com.fiap.restaurante.utils.AvaliacaoTestUtils;
import br.com.fiap.restaurante.utils.EspecialidadeTestUtils;
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

import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste (application-test.properties)
@Transactional
public class UpdateAvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void finish() {
        repository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void testUpdateAvaliacao() throws Exception {
        var avaliacao = repository.save(AvaliacaoTestUtils.getDefaultAvaliacao(
                RestauranteTestUtils.getDefaultRestaurante()
        ));

        var request = AvaliacaoTestUtils.getDefaultRequestUpdateAvaliacaoDTO();

        mockMvc.perform(put("/update-avaliacao/{id}", avaliacao.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.dataUpdate").isNotEmpty())
                .andExpect(jsonPath("$.valorAvaliacao", is(request.valorAvaliacao().toString())))
                .andExpect(jsonPath("$.comentario", is(request.comentario())));;

        // Verifica se a especialidade foi salva no banco de dados
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    public void testNotFoundAvaliacaoInUpdateAvaliacao() throws Exception {
        var request = AvaliacaoTestUtils.getDefaultRequestUpdateAvaliacaoDTO();

        mockMvc.perform(put("/update-avaliacao/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("UpdateAvaliacao: identificador da avaliacao não encontrada"));
    }

    @Test
    public void testInvalidParamCreateRestaurante() throws Exception {
        var request = AvaliacaoTestUtils.getDefaultInvalidRequestUpdateAvaliacaoDTO();

        mockMvc.perform(put("/update-avaliacao/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.length()").value(2))
                .andExpect(
                        jsonPath("$.messages[*].campo")
                                .value(
                                        containsInAnyOrder(
                                                "valorAvaliacao",
                                                "comentario"
                                        )
                                ));
    }
}