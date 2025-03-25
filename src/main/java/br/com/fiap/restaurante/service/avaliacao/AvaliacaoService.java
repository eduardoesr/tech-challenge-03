package br.com.fiap.restaurante.service.avaliacao;

import br.com.fiap.restaurante.dto.avaliacao.AvaliacaoDTO;
import br.com.fiap.restaurante.model.Avaliacao;
import br.com.fiap.restaurante.model.Restaurante;
import br.com.fiap.restaurante.repository.AvaliacaoRepository;

public abstract class AvaliacaoService {

    AvaliacaoRepository repository;

    protected AvaliacaoService() {}

    protected AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public static Avaliacao toEntity(AvaliacaoDTO avaliacaoDTO, Restaurante restaurante) {
        return new Avaliacao(
                restaurante,
                avaliacaoDTO.comentario(),
                avaliacaoDTO.nomeCliente(),
                avaliacaoDTO.dataCriacao(),
                avaliacaoDTO.dataUpdate(),
                avaliacaoDTO.valorAvaliacao()
        );
    }

    public static AvaliacaoDTO toAvaliacaoDTO(Avaliacao avaliacao) {
        return new AvaliacaoDTO(
                avaliacao.getId(),
                avaliacao.getRestaurante().getId(),
                avaliacao.getComentario(),
                avaliacao.getNomeCliente(),
                avaliacao.getDataCriacao(),
                avaliacao.getDataUpdate(),
                avaliacao.getValorAvaliacao()
        );
    }
}