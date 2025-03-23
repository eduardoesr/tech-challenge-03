package br.com.fiap.restaurante.service.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.avaliacao.RequestCreateAvaliacaoDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Avaliacao;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import br.com.fiap.restaurante.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateAvaliacaoService extends AvaliacaoService {

    RestauranteRepository restauranteRepository;

    @Autowired
    public CreateAvaliacaoService(
            AvaliacaoRepository repository,
            RestauranteRepository restauranteRepository
    ) {
        super(repository);
        this.restauranteRepository = restauranteRepository;
    }

    public AvaliacaoDTO create(RequestCreateAvaliacaoDTO createDTO) {
        Restaurante restaurante = restauranteRepository
                .findById(createDTO.restauranteId())
                .orElseThrow(() -> new NotFoundServiceError("CreateAvaliacaoService: identificador restaurante não econtrado"));

        if(createDTO.nomeCliente().isEmpty()) {
            createDTO = new RequestCreateAvaliacaoDTO(
                    createDTO.restauranteId(),
                    createDTO.comentario(),
                    "Anônimo",
                    createDTO.valorAvaliacao()
            );
        }

        Avaliacao avaliacao = toEntity(createDTO, restaurante, LocalDateTime.now());

        return toAvaliacaoDTO(repository.save(avaliacao));
    }

    public static Avaliacao toEntity(
            RequestCreateAvaliacaoDTO especialidadeDTO,
            Restaurante restaurante,
            LocalDateTime dataCriacao
    ) {
        return new Avaliacao(
                restaurante,
                especialidadeDTO.comentario(),
                especialidadeDTO.nomeCliente(),
                null,
                dataCriacao,
                especialidadeDTO.valorAvaliacao()
        );
    }
}
