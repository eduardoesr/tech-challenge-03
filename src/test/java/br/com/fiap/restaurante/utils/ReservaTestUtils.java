package br.com.fiap.restaurante.utils;

import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.reserva.RequestCreateReservaDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.model.context.DiasFuncionamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservaTestUtils {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public ReservaTestUtils(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public Long createEspecialidade() throws Exception {
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

    public Long createRestaurante(Long especialidadeId) throws Exception {
        Set<DiasFuncionamento> diasFuncionamentos = Set.of(DiasFuncionamento.SEG);

        RequestCreateRestauranteDTO restauranteRequest = new RequestCreateRestauranteDTO(
                especialidadeId,
                10,
                "Nome Restaurante",
                0,
                0,
                "Endereço",
                LocalTime.parse("17:00:00"),
                LocalTime.parse("22:00:00"),
                diasFuncionamentos
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

    public LocalDateTime obterDataReserva(Boolean dataValida, Boolean horarioValido, Boolean horarioApos) {
        DayOfWeek dia = dataValida ? DayOfWeek.MONDAY : DayOfWeek.SUNDAY;
        int horario = horarioValido ? 17 : 15;
        if (horarioApos) {
            horario = 23;
        }
        LocalDateTime dataReseva = LocalDate.now().with(dia).atTime(LocalTime.of(horario, 0));
        if (dataReseva.isBefore(LocalDateTime.now())) {
            dataReseva = dataReseva.plusWeeks(1);
        }
        return dataReseva;
    }

    public LocalDateTime obterDataReserva(Boolean dataValida, Boolean horarioValido) {
        return obterDataReserva(dataValida, horarioValido, false);
    }

    public Long createReserva(Long restauranteId) throws Exception {
        RequestCreateReservaDTO reservaRequest = new RequestCreateReservaDTO(
                restauranteId,
                "Nome cliente",
                2,
                obterDataReserva(true, true)
        );

        String reservaResponse = mockMvc.perform(post("/create-reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(reservaResponse).get("id").asLong();
    }
}
