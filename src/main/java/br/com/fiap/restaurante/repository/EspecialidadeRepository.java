package br.com.fiap.restaurante.repository;

import br.com.fiap.restaurante.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
}
