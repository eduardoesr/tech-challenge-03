package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.service.avaliacao.DeleteAvaliacaoService;
import br.com.fiap.restaurante.service.especialidade.DeleteEspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delete-avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificão a entidade Avaliacao")
public class DeleteAvaliacaoController {

    @Autowired
    DeleteAvaliacaoService service;

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