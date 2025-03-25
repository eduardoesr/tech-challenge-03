package br.com.fiap.restaurante.controller.especialidade;


import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o
class DeleteEspecialidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EspecialidadeRepository repository;

    @Autowired
    private ObjectMapper objectMapper; // Para serializar/deserializar JSON

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testDeleteEspecialidade() throws Exception {
        //Insere um item antes de deleta-lo
        var especialidade = repository.save(new Especialidade(
            "test",
            "Especialidade Test",
            null
        ));

        mockMvc.perform(delete("/delete-especialidade/{id}", especialidade.getId()))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(especialidade.getId())).isEmpty();
    }

    @Test
    public void testDeleteEspecialidadeInexistente() throws Exception {
        mockMvc.perform(delete("/delete-especialidade/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("DeleteEspecialidade: identificador não foi encontrado"));
    }

}