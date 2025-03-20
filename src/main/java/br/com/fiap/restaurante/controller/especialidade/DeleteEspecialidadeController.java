package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.service.especialidade.DeleteEspecialidadeService;
import br.com.fiap.restaurante.service.restaurante.DeleteRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete-especialidade")
@Tag(name="Especialidade", description="Endpoints que modificão a entidade Especialidade")
public class DeleteEspecialidadeController {

    @Autowired
    DeleteEspecialidadeService service;

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um especialidade",
            description = "Retira uma ocorrencia de especialidade"
    )
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}