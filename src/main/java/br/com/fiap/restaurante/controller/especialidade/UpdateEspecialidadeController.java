package br.com.fiap.restaurante.controller.especialidade;

import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestCreateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.especialidade.RequestUpdateEspecialidadeDTO;
import br.com.fiap.restaurante.dto.restaurante.RequestUpdateRestauranteDTO;
import br.com.fiap.restaurante.dto.restaurante.RestauranteDTO;
import br.com.fiap.restaurante.service.especialidade.UpdateEspecialidadeService;
import br.com.fiap.restaurante.service.restaurante.UpdateRestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update-especialidade")
@Tag(name="Especialidade", description="Endpoints que modificão a entidade Especialidade")
public class UpdateEspecialidadeController {

    @Autowired
    UpdateEspecialidadeService service;

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza informações sobre uma especialidade",
            description = "Atualiza informações sobre de uma especialidade ja existente"
    )
    public ResponseEntity<EspecialidadeDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RequestUpdateEspecialidadeDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
