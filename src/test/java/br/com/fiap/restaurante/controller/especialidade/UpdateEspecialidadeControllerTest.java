package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.utils.EspecialidadeTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste (application-test.properties)
class UpdateEspecialidadeControllerTest {

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
    void testUpdateEspecialidade() throws Exception {

        RequestUpdateEspecialidadeDTO request = new RequestUpdateEspecialidadeDTO(
                "new name",
                "new descricao"
        );

        Especialidade especialidade = repository.save(EspecialidadeTestUtils.getDefaultEspecialidade());

        mockMvc.perform(put("/update-especialidade/{id}", especialidade.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // Verifica se o status é 201 Created
                .andExpect(jsonPath("$.nome", is(request.nome()))) // Verifica o campo "nome"
                .andExpect(jsonPath("$.descricao", is(request.descricao()))); // Verifica o campo "descricao"

        // Verifica se a especialidade foi salva no banco de dados
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void testUpdateEspecialidadeInexistente() throws Exception {
        RequestUpdateEspecialidadeDTO request = EspecialidadeTestUtils.getDefaultRequestUpdateEspecialidadeDTO();

        mockMvc.perform(put("/update-especialidade/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("UpdateEspecialidade: identificador da especialidade não encontrada"));
    }

    @Test
    void testInvalidParamUpdateEspecialidade() throws Exception {
        RequestUpdateEspecialidadeDTO request = EspecialidadeTestUtils.getDefaultInvalidRequestUpdateEspecialidadeDTO();

        Long id = repository.save(EspecialidadeTestUtils.getDefaultEspecialidade()).getId();

        mockMvc.perform(put("/update-especialidade/{id}",id)
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