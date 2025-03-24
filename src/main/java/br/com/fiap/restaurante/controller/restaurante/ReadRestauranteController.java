package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.service.restaurante.ReadRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
@Tag(name="Restaurante", description="Endpoints que modificam a entidade Restaurante")
public class ReadRestauranteController {

    @Autowired
    ReadRestauranteService service;

    @GetMapping
    @Operation(
        summary = "Lista todos os restaurantes",
        description = "Exibe uma lista de restaurante"
    )
    public ResponseEntity<List<RestauranteDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista um restaurante e suas dependencias",
            description = "Exibe informações sobre um restaurante"
    )
    public ResponseEntity<RestauranteDTO> findById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
