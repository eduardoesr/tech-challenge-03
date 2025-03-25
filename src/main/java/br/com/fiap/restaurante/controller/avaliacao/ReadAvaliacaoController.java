package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.service.avaliacao.ReadAvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificão a entidade Avaliacao")
public class ReadAvaliacaoController {

    final ReadAvaliacaoService service;

    public ReadAvaliacaoController(ReadAvaliacaoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Localiza uma avaliação",
            description = "Lista uma avaliação feita a um restaurante"
    )
    public ResponseEntity<AvaliacaoDTO> findById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
