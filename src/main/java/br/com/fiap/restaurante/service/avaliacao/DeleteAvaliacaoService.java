package br.com.fiap.restaurante.service.avaliacao;

import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.AvaliacaoRepository;
import br.com.fiap.restaurante.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAvaliacaoService extends AvaliacaoService {

    @Autowired
    public DeleteAvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public void deleteById(Long id) {
        if(!repository.existsById(id)){
            throw new NotFoundServiceError("DeleteAvaliacao: identificador não foi encontrado");
        }
        repository.deleteById(id);
    }
}