package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.service.avaliacao.ReadAvaliacaoService;
import br.com.fiap.restaurante.service.especialidade.ReadEspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificão a entidade Avaliacao")
public class ReadAvaliacaoController {

    @Autowired
    ReadAvaliacaoService service;

    /*@GetMapping
    @Operation(
        summary = "Lista todos os comentarios de um restaurante",
        description = "Exibe uma lista de avaliações de um restaurante"
    )
    public ResponseEntity<Page<EspecialidadeDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }*/

    @GetMapping("/{id}")
    @Operation(
            summary = "Localiza uma avaliação",
            description = "Lista uma avaliação feita a um restaurante"
    )
    public ResponseEntity<AvaliacaoDTO> findById(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
