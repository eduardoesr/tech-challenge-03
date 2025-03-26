package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.avaliacao.RequestUpdateAvaliacaoDTO;
import br.com.fiap.restaurante.service.avaliacao.UpdateAvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update-avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificam a entidade Avaliacao")
public class UpdateAvaliacaoController {

    final UpdateAvaliacaoService service;

    public UpdateAvaliacaoController(UpdateAvaliacaoService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza uma avaliação",
            description = "Atualiza informações sobre de uma avaliação"
    )
    public ResponseEntity<AvaliacaoDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RequestUpdateAvaliacaoDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
