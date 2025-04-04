package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestCreateReservaDTO;
import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.service.reserva.CreateReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-reserva")
@Tag(name="Reserva", description="Endpoints que modificam a entidade Reserva")
public class CreateReservaController {

    final CreateReservaService service;

    public CreateReservaController(CreateReservaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Cria uma reserva",
            description = "Cria uma reserva"
    )
    public ResponseEntity<ReservaDTO> create(@Validated @RequestBody RequestCreateReservaDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.create(request));
    }
}
