package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.utils.EspecialidadeTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReadEspecialidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EspecialidadeRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @AfterEach
    void after() {repository.deleteAll();}

    @Test
    void testReadEspecialidade() throws Exception {
        var especialidade = repository.save(EspecialidadeTestUtils.getDefaultEspecialidade());

        mockMvc.perform(get("/especialidade/{id}", especialidade.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(especialidade.getId()))
                .andExpect(jsonPath("$.nome").value(especialidade.getNome()))
                .andExpect(jsonPath("$.descricao").value(especialidade.getDescricao()));
    }

    @Test
    void testReadEspecialidadeInexistente() throws Exception {
        mockMvc.perform(get("/especialidade/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ReadEspecialidade: identificador não encontrado"));
    }

    @Test
    void testReadAllEspecialidade() throws Exception {
        repository.save(EspecialidadeTestUtils.getDefaultEspecialidade());
        repository.save(EspecialidadeTestUtils.getDefaultEspecialidade());

        mockMvc.perform(get("/especialidade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }
}
