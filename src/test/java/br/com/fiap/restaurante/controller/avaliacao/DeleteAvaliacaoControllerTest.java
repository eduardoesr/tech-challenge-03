package br.com.fiap.restaurante.controller.avaliacao;


import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import br.com.fiap.restaurante.utils.AvaliacaoTestUtils;
import br.com.fiap.restaurante.utils.RestauranteTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o
class DeleteAvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoRepository repository;

    @AfterEach
    void finish() {
        repository.deleteAll();
    }

    @Test
    void testDeleteAvaliacao() throws Exception {
        var avaliacao = repository.save(AvaliacaoTestUtils.getDefaultAvaliacao(null));

        mockMvc.perform(delete("/delete-avaliacao/{id}", avaliacao.getId()))
                .andExpect(status().isNoContent());

        assertThat(repository.findById(avaliacao.getId())).isEmpty();
    }

    @Test
    void testDeleteAvaliacaoInexistente() throws Exception {
        mockMvc.perform(delete("/delete-avaliacao/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("DeleteAvaliacao: identificador não foi encontrado"));
    }

}