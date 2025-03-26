package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.service.especialidade.ReadEspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especialidade")
@Tag(name="Especialidade", description="Endpoints que modificam a entidade Especialidade")
public class ReadEspecialidadeController {

    final ReadEspecialidadeService service;

    public ReadEspecialidadeController(ReadEspecialidadeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
        summary = "Lista todas as especialidades",
        description = "Exibe uma lista de especialidades"
    )
    public ResponseEntity<Page<EspecialidadeDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista uma especialidade e suas dependencias",
            description = "Exibe informações sobre uma especialidade"
    )
    public ResponseEntity<EspecialidadeDTO> findById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
