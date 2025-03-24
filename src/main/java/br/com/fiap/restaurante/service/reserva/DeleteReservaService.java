package br.com.fiap.restaurante.service.reserva;

import br.com.fiap.restaurante.error.service.NotFoundServiceError;
import br.com.fiap.restaurante.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteReservaService {

    ReservaRepository repository;

    @Autowired
    public DeleteReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
            return;
        }
        throw new NotFoundServiceError("DeleteReserva: identificador não foi encontrado");
    }
}
