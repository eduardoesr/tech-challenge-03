package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.avaliacao.RequestUpdateAvaliacaoDTO;
import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.service.avaliacao.UpdateAvaliacaoService;
import br.com.fiap.restaurante.service.especialidade.UpdateEspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update-avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificão a entidade Avaliacao")
public class UpdateAvaliacaoController {

    @Autowired
    UpdateAvaliacaoService service;

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
