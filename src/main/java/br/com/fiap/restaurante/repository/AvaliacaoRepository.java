package br.com.fiap.restaurante.repository;

import br.com.fiap.restaurante.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
