package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import br.com.fiap.restaurante.utils.AvaliacaoTestUtils;
import br.com.fiap.restaurante.utils.RestauranteTestUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ReadAvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoRepository repository;

    @AfterEach
    void finish() {
        repository.deleteAll();
    }

    @Test
    void testReadAvaliacao() throws Exception {
        var avaliacao = repository.save(AvaliacaoTestUtils.getDefaultAvaliacao(
                RestauranteTestUtils.getDefaultRestaurante()
        ));

        mockMvc.perform(get("/avaliacao/{id}", avaliacao.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario", is(avaliacao.getComentario())))
                .andExpect(jsonPath("$.nomeCliente", is(avaliacao.getNomeCliente())))
                .andExpect(jsonPath("$.dataCriacao", containsString(avaliacao.getDataCriacao().truncatedTo(ChronoUnit.MILLIS).toString())))
                .andExpect(jsonPath("$.id", is(avaliacao.getId().intValue())))
                .andExpect(jsonPath("$.valorAvaliacao", is(avaliacao.getValorAvaliacao().toString())));
    }

    @Test
    void testReadAvaliacaoInexistente() throws Exception {
        mockMvc.perform(get("/avaliacao/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ReadEspecialidade: identificador não encontrado"));
    }
}
