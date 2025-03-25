package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.service.reserva.ReadReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reserva")
@Tag(name="Reserva", description="Endpoints que listam a entidade Reserva")
public class ReadReservaController {

    @Autowired
    ReadReservaService service;

    @GetMapping
    @Operation(
        summary = "Lista todas as reservas",
        description = "Exibe uma lista de reservas"
    )
    public ResponseEntity<List<ReservaDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/restaurante/{id}")
    @Operation(
            summary = "Lista todas as reservas de um restaurante com o id especificado",
            description = "Exibe uma lista de reservas"
    )
    public ResponseEntity<List<ReservaDTO>> findByRestauranteId(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.findAllByRestauranteId(id));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista todas uma reserva de um restaurante",
            description = "Exibe uma informações sobre uma reserva"
    )
    public ResponseEntity<ReservaDTO> findById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
