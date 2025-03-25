package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste (application-test.properties)
public class CreateRestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long especialidadeId;

    @AfterEach
    void finish() {
        especialidadeRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        this.especialidadeId = especialidadeRepository.save(new Especialidade(
            "test",
            "uso nos test",
            null
        )).getId();
    }

    @Test
    public void testCreateEspecialidade() throws Exception {

        RequestCreateRestauranteDTO request = new RequestCreateRestauranteDTO(
                this.especialidadeId,
                10,
                "restaurante test",
                0,
                0,
                "endereco",
                LocalTime.now(),
                LocalTime.now(),
                Set.of(),
                LocalTime.now()
        );

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
                .andExpect(jsonPath("$.diasFuncionamentos.length()", is(request.diasFuncionamentos().size())))
                .andExpect(jsonPath("$.tolerancia", containsString(request.tolerancia().truncatedTo(ChronoUnit.MILLIS).toString())));


        // Verifica se a especialidade foi salva no banco de dados
        assertThat(especialidadeRepository.count()).isEqualTo(1);
    }

    /*@Test
    public void testInvalidParamCreateEspecialidade() throws Exception {
        RequestCreateEspecialidadeDTO request = new RequestCreateEspecialidadeDTO(
            "",
            ""
        );

        mockMvc.perform(post("/create-especialidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages.length()").value(2))
                .andExpect(
                        jsonPath("$.messages[*].campo")
                                .value(containsInAnyOrder("nome", "descricao"))
                );
    }*/
}