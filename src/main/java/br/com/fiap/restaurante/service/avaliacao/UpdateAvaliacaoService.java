package br.com.fiap.restaurante.service.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.avaliacao.RequestUpdateAvaliacaoDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateAvaliacaoService extends AvaliacaoService {

    @Autowired
    public UpdateAvaliacaoService(
            AvaliacaoRepository repository
    ) {
        this.repository = repository;
    }

    public AvaliacaoDTO update(Long id, RequestUpdateAvaliacaoDTO avaliacaoDTO) {
        try {
            var especialidade = repository.getReferenceById(id);
            especialidade.setComentario(avaliacaoDTO.comentario());
            especialidade.setValorAvaliacao(avaliacaoDTO.valorAvaliacao());
            especialidade.setDataUpdate(LocalDateTime.now());
            return toAvaliacaoDTO(repository.save(especialidade));
        } catch (EntityNotFoundException e) {
            throw new NotFoundServiceError("UpdateEspecialidade: identificador da especialidade não encontrada");
        }
    }
}
