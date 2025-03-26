package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.service.especialidade.CreateEspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-especialidade")
@Tag(name="Especialidade", description="Endpoints que modificam a entidade Especialidade")
public class CreateEspecialidadeController {

    final CreateEspecialidadeService service;

    public CreateEspecialidadeController(CreateEspecialidadeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Cria uma especialidade",
            description = "Insere um especialidade na base"
    )
    public ResponseEntity<EspecialidadeDTO> create(@Valid @RequestBody RequestCreateEspecialidadeDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.create(request));
    }

}
