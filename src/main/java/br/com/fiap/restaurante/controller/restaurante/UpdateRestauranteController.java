package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RequestUpdateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.service.restaurante.UpdateRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update-restaurante")
@Tag(name="Restaurante", description="Endpoints que modificam a entidade Restaurante")
public class UpdateRestauranteController {

    final UpdateRestauranteService service;

    public UpdateRestauranteController(UpdateRestauranteService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza informações sobre um restaurante",
            description = "Atualiza informações sobre de um restaurante ja existente"
    )
    public ResponseEntity<RestauranteDTO> update(@PathVariable Long id,@Valid @RequestBody RequestUpdateRestauranteDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
