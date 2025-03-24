package br.com.fiap.restaurante.controller.restaurante;

import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.service.restaurante.CreateRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create-restaurante")
@Tag(name="Restaurante", description="Endpoints que modificam a entidade Restaurante")
public class CreateRestauranteController {

    @Autowired
    CreateRestauranteService service;

    @PostMapping
    @Operation(
            summary = "Cria um restaurante",
            description = "Insere um restaurante na base"
    )
    public ResponseEntity<RestauranteDTO> create(@Valid @RequestBody RequestCreateRestauranteDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.create(request));
    }

}
