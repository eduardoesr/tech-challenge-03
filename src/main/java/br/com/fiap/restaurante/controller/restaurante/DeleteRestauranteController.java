package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.service.restaurante.DeleteRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("delete-restaurante")
@Tag(name="Restaurante", description="Endpoints que modificam a entidade Restaurante")
public class DeleteRestauranteController {

    final DeleteRestauranteService service;

    public DeleteRestauranteController(DeleteRestauranteService service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um restaurante",
            description = "Retira uma ocorrencia de restaurante"
    )
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}