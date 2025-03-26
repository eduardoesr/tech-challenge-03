package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste (application-test.properties)
@Transactional
class CreateRestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    private Long especialidadeId;

    @AfterEach
    void finish() {
        especialidadeRepository.deleteAll();
        repository.deleteAll();
    }

    @BeforeEach
    void setup() {
        especialidadeRepository.deleteAll();
        repository.deleteAll();
        this.especialidadeId = especialidadeRepository.save(EspecialidadeTestUtils.getDefaultEspecialidade()).getId();
    }

    @Test
    void testCreateRestaurante() throws Exception {

        RequestCreateRestauranteDTO request = RestauranteTestUtils.getDefaultRequestCreateRestauranteDTO(this.especialidadeId);

        mockMvc.perform(post("/create-restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Verifica se o status é 201 Created
                .andExpect(jsonPath("$.nome", is(request.nome())))
                .andExpect(jsonPath("$.especialidade.id", is(request.especialidadeId().intValue())))
                .andExpect(jsonPath("$.capacidadePessoas", is(request.capacidadePessoas())))
                .andExpect(jsonPath("$.latitude", is(request.latitude())))
                .andExpect(jsonPath("$.longitude", is(request.longitude())))
                .andExpect(jsonPath("$.enderecoCompleto", is(request.enderecoCompleto())))
                .andExpect(jsonPath("$.horarioAbertura", containsString(request.horarioAbertura().truncatedTo(ChronoUnit.MILLIS).toString())))//Adicionando truncatedTo para ligar com problemas de precisão em comparação
                .andExpect(jsonPath("$.horarioFechamento", containsString(request.horarioFechamento().truncatedTo(ChronoUnit.MILLIS).toString())))
                .andExpect(jsonPath("$.diasFuncionamentos.length()", is(request.diasFuncionamentos().size())));

        // Verifica se a especialidade foi salva no banco de dados
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void testNotFoundEspecialidadeInCreateRestaurante() throws Exception {
        RequestCreateRestauranteDTO request = RestauranteTestUtils.getDefaultRequestCreateRestauranteDTO(0L);

        mockMvc.perform(post("/create-restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CreateRestaurante: identificador da especialidade não encontrado"));
    }

    @Test
    void testInvalidParamCreateRestaurante() throws Exception {
        RequestCreateRestauranteDTO request = RestauranteTestUtils.getDefaultInvalidRequestCreateRestauranteDTO();

        mockMvc.perform(post("/create-restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.length()").value(9))
                .andExpect(
                        jsonPath("$.messages[*].campo")
                                .value(
                                        containsInAnyOrder(
                                                "nome",
                                                "especialidadeId",
                                                "capacidadePessoas",
                                                "latitude",
                                                "longitude",
                                                "enderecoCompleto",
                                                "horarioAbertura",
                                                "horarioFechamento",
                                                "diasFuncionamentos"
                                        )
                                ));
    }
}