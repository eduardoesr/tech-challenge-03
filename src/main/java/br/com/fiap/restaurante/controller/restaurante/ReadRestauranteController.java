package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import br.com.fiap.restaurante.service.restaurante.ReadResturanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
@Tag(name="Restaurante", description="Endpoints que modificão a entidade Restaurante")
public class ReadRestauranteController {

    @Autowired
    ReadResturanteService service;

    @GetMapping
    @Operation(
        summary = "Lista todos os restaurantes",
        description = "Exibe uma lista de restaurante"
    )
    public ResponseEntity<List<RestauranteDTO>> findAll(@RequestBody Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista um restaurante e suas dependencias",
            description = "Exibe informações sobre um restaurante"
    )
    public ResponseEntity<RestauranteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
