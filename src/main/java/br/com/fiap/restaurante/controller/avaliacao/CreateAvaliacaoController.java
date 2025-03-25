package br.com.fiap.restaurante.controller.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.avaliacao.RequestCreateAvaliacaoDTO;
import br.com.fiap.restaurante.service.avaliacao.CreateAvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-avaliacao")
@Tag(name="Avaliacao", description="Endpoints que modificão a entidade Avaliacao")
public class CreateAvaliacaoController {

    final CreateAvaliacaoService service;

    public CreateAvaliacaoController(CreateAvaliacaoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Cria uma avaliacao para um restaurante",
            description = "Insere uma avaliacao a um restaurante"
    )
    public ResponseEntity<AvaliacaoDTO> create(@Valid @RequestBody RequestCreateAvaliacaoDTO request) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(service.create(request));
    }

}
