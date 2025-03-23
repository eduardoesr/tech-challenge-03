package br.com.fiap.restaurante.service.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.dto.especialidade.EspecialidadeDTO;
import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.model.Avaliacao;
import br.com.fiap.restaurante.model.Especialidade;
import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReadAvaliacaoService extends AvaliacaoService {

    @Autowired
    public ReadAvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public AvaliacaoDTO findById(Long id) {
        Avaliacao especialidade = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundServiceError("ReadEspecialidade: identificador não encontrado"));
        return toAvaliacaoDTO(especialidade);
    }

    public Page<AvaliacaoDTO> findAll(Pageable pageable) {
        Page<Avaliacao> avaliacoes = repository.findAll(pageable);
        return avaliacoes.map(AvaliacaoService::toAvaliacaoDTO);
    }
}
