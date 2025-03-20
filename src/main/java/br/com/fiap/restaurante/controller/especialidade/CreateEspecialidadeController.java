package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestCreateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.service.especialidade.CreateEspecialidadeService;
import br.com.fiap.restaurante.service.restaurante.CreateRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-especialidade")
@Tag(name="Especialidade", description="Endpoints que modificão a entidade Especialidade")
public class CreateEspecialidadeController {

    @Autowired
    CreateEspecialidadeService service;

    @PostMapping
    @Operation(
            summary = "Cria uma especialidade",
            description = "Insere um especialidade na base"
    )
    public ResponseEntity<EspecialidadeDTO> create(@Valid @RequestBody RequestUpdateEspecialidadeDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.create(request));
    }

}
