package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.service.avaliacao.DeleteAvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delete-avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificam a entidade Avaliacao")
public class DeleteAvaliacaoController {

    final DeleteAvaliacaoService service;

    public DeleteAvaliacaoController(DeleteAvaliacaoService service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta uma avaliacao",
            description = "Retira uma avaliacao de um restaurante"
    )
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}