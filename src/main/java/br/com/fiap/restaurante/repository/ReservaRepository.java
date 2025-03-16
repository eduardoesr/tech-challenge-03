package br.com.fiap.restaurante.repository;

import br.com.fiap.restaurante.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
