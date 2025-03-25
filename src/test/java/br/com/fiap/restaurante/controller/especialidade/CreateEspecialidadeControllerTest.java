package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste (application-test.properties)
public class CreateEspecialidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EspecialidadeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void after() {
        repository.deleteAll();
    }

    @Test
    void testCreateEspecialidade() throws Exception {

        RequestCreateEspecialidadeDTO request = new RequestCreateEspecialidadeDTO(
                "Pizza",
                "Pizza de Calabresa"
        );

        mockMvc.perform(post("/create-especialidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Verifica se o status é 201 Created
                .andExpect(jsonPath("$.nome", is("Pizza"))) // Verifica o campo "nome"
                .andExpect(jsonPath("$.descricao", is("Pizza de Calabresa"))); // Verifica o campo "descricao"

        // Verifica se a especialidade foi salva no banco de dados
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void testInvalidParamCreateEspecialidade() throws Exception {
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
    }
}