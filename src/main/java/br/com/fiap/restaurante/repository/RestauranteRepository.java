package br.com.fiap.restaurante.repository;

import br.com.fiap.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
