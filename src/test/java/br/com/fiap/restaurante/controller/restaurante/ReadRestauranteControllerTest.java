package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import br.com.fiap.restaurante.repository.ReservaRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import br.com.fiap.restaurante.utils.EspecialidadeTestUtils;
import br.com.fiap.restaurante.utils.RestauranteTestUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class ReadRestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

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
    void testReadRestaurante() throws Exception {
        var restaurante = RestauranteTestUtils.getDefaultRestaurante();

        restaurante.setEspecialidade(especialidadeRepository.getReferenceById(especialidadeId));

        restaurante = repository.save(restaurante);

        mockMvc.perform(get("/restaurante/{id}", restaurante.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(restaurante.getNome())))
                .andExpect(jsonPath("$.capacidadePessoas", is(restaurante.getCapacidadePessoas())))
                .andExpect(jsonPath("$.latitude", is(restaurante.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(restaurante.getLongitude())))
                .andExpect(jsonPath("$.enderecoCompleto", is(restaurante.getEnderecoCompleto())))
                .andExpect(jsonPath("$.horarioAbertura", containsString(restaurante.getHorarioAbertura().truncatedTo(ChronoUnit.MILLIS).toString())))//Adicionando truncatedTo para ligar com problemas de precisão em comparação
                .andExpect(jsonPath("$.horarioFechamento", containsString(restaurante.getHorarioFechamento().truncatedTo(ChronoUnit.MILLIS).toString())))
                .andExpect(jsonPath("$.diasFuncionamentos.length()", is(restaurante.getDiasFuncionamento().size())));
    }

    @Test
    void testReadRestauranteInexistente() throws Exception {
        mockMvc.perform(get("/restaurante/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ReadRestaurante: identificador não encontrado"));
    }

    @Test
    void testReadAllRestaurante() throws Exception {
        var restauranteA = RestauranteTestUtils.getDefaultRestaurante();
        restauranteA.setEspecialidade(especialidadeRepository.getReferenceById(especialidadeId));
        repository.save(restauranteA);

        var restauranteB = RestauranteTestUtils.getDefaultRestaurante();
        restauranteB.setEspecialidade(especialidadeRepository.getReferenceById(especialidadeId));
        repository.save(restauranteB);

        mockMvc.perform(get("/restaurante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void testReadAllRestauranteWithNomeFilter() throws Exception {
        var restauranteA = RestauranteTestUtils.getDefaultRestaurante();
        restauranteA.setNome("Restaurante A");
        restauranteA.setEspecialidade(especialidadeRepository.getReferenceById(especialidadeId));
        repository.save(restauranteA);

        var restauranteB = RestauranteTestUtils.getDefaultRestaurante();
        restauranteB.setNome("Restaurante B");
        restauranteB.setEspecialidade(especialidadeRepository.getReferenceById(especialidadeId));
        repository.save(restauranteB);

        // Perform the request with a filter by "nome"
        mockMvc.perform(get("/restaurante")
                        .param("nome", "Restaurante A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1)) // Expect only one result
                .andExpect(jsonPath("$.content[0].nome", is("Restaurante A"))); // Validate the filtered result
    }
}
