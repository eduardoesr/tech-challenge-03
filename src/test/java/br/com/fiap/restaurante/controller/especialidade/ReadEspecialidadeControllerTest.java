package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReadEspecialidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EspecialidadeRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testReadEspecialidade() throws Exception {
        Especialidade especialidade = new Especialidade(
                "Especialidade Descricao",
                "Especialiade",
                null
        );

        especialidade = repository.save(especialidade);

        mockMvc.perform(get("/especialidade/{id}", especialidade.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(especialidade.getId()))
                .andExpect(jsonPath("$.nome").value(especialidade.getNome()))
                .andExpect(jsonPath("$.descricao").value(especialidade.getDescricao()));
    }
}
