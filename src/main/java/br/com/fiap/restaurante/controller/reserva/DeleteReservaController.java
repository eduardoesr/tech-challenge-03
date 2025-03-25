package br.com.fiap.restaurante.controller.reserva;

import br.com.fiap.restaurante.service.reserva.DeleteReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete-reserva")
@Tag(name="Reserva", description="Endpoints que modificam a entidade Reserva")
public class DeleteReservaController {

    final DeleteReservaService service;

    public DeleteReservaController(DeleteReservaService service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta uma reserva",
            description = "Retira uma ocorrencia de resreva"
    )
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
