package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.service.restaurante.DeleteRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete-restaurante")
@Tag(name="Especialidade", description="Endpoints que modificão a entidade Especialidade")
public class DeleteRestauranteController {

    @Autowired
    DeleteRestauranteService service;

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um restaurante",
            description = "Retira uma ocorrencia de restaurante"
    )
    public ResponseEntity<Void> delete(@NotBlank @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}