package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.dto.reserva.RequestUpdateReservaDTO;
import br.com.fiap.restaurante.dto.reserva.ReservaDTO;
import br.com.fiap.restaurante.model.context.StatusReserva;
import br.com.fiap.restaurante.service.reserva.UpdateReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update-reserva")
@Tag(name="Reserva", description="Endpoints que modificam a entidade uma reserva")
public class UpdateReservaController {

    final UpdateReservaService service;

    public UpdateReservaController(UpdateReservaService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza uma reserva.",
            description = "Atualiza um parquimetro."
    )
    public ResponseEntity<ReservaDTO> update(@PathVariable Long id, @Valid @RequestBody RequestUpdateReservaDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @PutMapping("/{id}/iniciar")
    @Operation(
            summary = "Inicia a sessão de uma reserva.",
            description = "Inicia a sessão de uma reserva."
    )
    public ResponseEntity<ReservaDTO> updateIniciar(@PathVariable Long id) {
        return ResponseEntity.ok(service.updateSimples(id, StatusReserva.EM_ANDAMENTO));
    }

    @PutMapping("/{id}/finalizar")
    @Operation(
            summary = "Finaliza a sessão de uma reserva.",
            description = "Finaliza a sessão de uma reserva."
    )
    public ResponseEntity<ReservaDTO> updateFinalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.updateSimples(id, StatusReserva.CONCLUIDO));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(
            summary = "Cancela a sessão de uma reserva.",
            description = "Cancela a sessão de uma reserva."
    )
    public ResponseEntity<ReservaDTO> updateCancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.updateSimples(id, StatusReserva.CANCELADO));
    }
}
