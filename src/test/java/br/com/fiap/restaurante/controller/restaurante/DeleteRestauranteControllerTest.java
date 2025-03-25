package br.com.fiap.restaurante.controller.restaurante;


import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import br.com.fiap.restaurante.utils.EspecialidadeTestUtils;
import br.com.fiap.restaurante.utils.RestauranteTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o
class DeleteRestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository repository;

    @AfterEach
    void finish() {
        repository.deleteAll();
    }

    @Test
    void testDeleteResturante() throws Exception {
        var restaurante = repository.save(RestauranteTestUtils.getDefaultRestaurante());

        mockMvc.perform(delete("/delete-restaurante/{id}", restaurante.getId()))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(restaurante.getId())).isEmpty();
    }

    @Test
    void testDeleteRestauranteInexistente() throws Exception {
        mockMvc.perform(delete("/delete-especialidade/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("DeleteEspecialidade: identificador não foi encontrado"));
    }

}