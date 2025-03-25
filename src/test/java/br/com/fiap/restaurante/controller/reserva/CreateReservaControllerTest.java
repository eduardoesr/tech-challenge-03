package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.reserva.RequestCreateReservaDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.model.context.DiasFuncionamento;
import br.com.fiap.restaurante.repository.ReservaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CreateReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    private Long createEspecialidade() throws Exception {
        RequestCreateEspecialidadeDTO especialidadeRequest = new RequestCreateEspecialidadeDTO(
                "Nome Especialidade",
                "Descrição"
        );

        String especialidadeResponse = mockMvc.perform(post("/create-especialidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(especialidadeRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(especialidadeResponse).get("id").asLong();
    }

    private Long createRestaurante(Long especialidadeId) throws Exception {
        Set<DiasFuncionamento> diasFuncionamentos = Set.of(DiasFuncionamento.SEG);

        RequestCreateRestauranteDTO restauranteRequest = new RequestCreateRestauranteDTO(
                especialidadeId,
                10,
                "Nome Restaurante",
                0,
                0,
                "Endereço",
                LocalTime.parse("17:00:00"),
                LocalTime.parse("23:00:00"),
                diasFuncionamentos,
                LocalTime.parse("00:15:00")
        );

        String restauranteResponse = mockMvc.perform(post("/create-restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(restauranteResponse).get("id").asLong();
    }

    private LocalDateTime obterDataReserva(Boolean dataValida, Boolean horarioValido) {
        DayOfWeek dia = dataValida ? DayOfWeek.MONDAY : DayOfWeek.SUNDAY;
        int horario = horarioValido ? 17 : 15;
        LocalDateTime dataReseva = LocalDate.now().with(dia).atTime(LocalTime.of(horario, 0));
        if (dataReseva.isBefore(LocalDateTime.now())) {
            dataReseva = dataReseva.plusWeeks(1);
        }
        return dataReseva;
    }

    @Test
    void testCreateReserva() throws Exception {
        Long especialidadeId = createEspecialidade();
        Long restauranteId = createRestaurante(especialidadeId);

        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                2,
                obterDataReserva(true, true)
        );

        mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.nomeCliente", is("Nome cliente")))
                .andExpect(jsonPath("$.quantidadePessoas", is(2)));
        assertThat(repository.count()).isEqualTo(1);
    }

     @Test
     void testCreateReservaRestauranteNaoEncontrado() throws Exception {
         RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                 999L, // Id não existente
                 "Nome cliente",
                 2,
                 obterDataReserva(true, true)
         );

         mockMvc.perform(post("/create-reserva")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservaRequest)))
                 .andExpect(status().isNotFound())
                 .andExpect(jsonPath("$.message", is("CreateReserva: Identificador do restaurante não encontrado.")));
     }

     @Test
     void testCreateReservaRestauranteFechado() throws Exception {
         Long especialidadeId = createEspecialidade();
         Long restauranteId = createRestaurante(especialidadeId);

         RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                 restauranteId,
                 "Nome cliente",
                 2,
                 obterDataReserva(false, true) // Restaurante não abre no dia.
         );

         mockMvc.perform(post("/create-reserva")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservaRequest)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString())) // Debugging step
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("CreateReserva: O restaurante não está aberto neste dia.")));
     }

     @Test
     void testCreateReservaAcimaDaCapacidade() throws Exception {
         Long especialidadeId = createEspecialidade();
         Long restauranteId = createRestaurante(especialidadeId);

         RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                 restauranteId,
                 "Nome cliente",
                 20, // Acima da capacidade
                 obterDataReserva(true, true)
         );

         mockMvc.perform(post("/create-reserva")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservaRequest)))
                 .andExpect(status().isNotFound())
                 .andExpect(jsonPath("$.message", is("CreateReserva: Acima da capacidade.")));
     }

     @Test
     public void testCreateReservaOutsideOperatingHours() throws Exception {
         Long especialidadeId = createEspecialidade();
         Long restauranteId = createRestaurante(especialidadeId);

         RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                 restauranteId,
                 "Nome cliente",
                 2,
                 obterDataReserva(true, false) // Antes de abrir
         );

         mockMvc.perform(post("/create-reserva")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservaRequest)))
                 .andExpect(status().isNotFound())
                 .andExpect(jsonPath("$.message", is("CreateReserva: A reserva deve ser em horário de serviço do restaurante.")));
     }

     @Test
     public void testCreateReservaExceedsTolerance() throws Exception {
         Long especialidadeId = createEspecialidade();
         Long restauranteId = createRestaurante(especialidadeId);

         RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                 restauranteId,
                 "Nome cliente",
                 2,
                 LocalDateTime.now().minusMinutes(20) // Exceeds tolerance
         );

         mockMvc.perform(post("/create-reserva")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(reservaRequest)))
                 .andExpect(status().isNotFound())
                 .andExpect(jsonPath("$.message", is("CreateReserva: O tempo de tolerância para a reserva foi excedido.")));
     }
}