package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.model.context.ValorAvaliacao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurante_id")
    @JsonManagedReference
    private Restaurante restaurante;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "data_update")
    private LocalDateTime dataUpdate;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "valor_avaliacao")
    private ValorAvaliacao valorAvaliacao;

    public Avaliacao() {
    }

    public Avaliacao(Restaurante restaurante, String comentario, String nomeCliente, LocalDateTime dataUpdate, LocalDateTime dataCriacao, ValorAvaliacao valorAvaliacao) {
        this.restaurante = restaurante;
        this.comentario = comentario;
        this.nomeCliente = nomeCliente;
        this.dataUpdate = dataUpdate;
        this.dataCriacao = dataCriacao;
        this.valorAvaliacao = valorAvaliacao;
    }

    public LocalDateTime getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(LocalDateTime dataUpdate) {
        this.dataUpdate = dataUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public ValorAvaliacao getValorAvaliacao() {
        return valorAvaliacao;
    }

    public void setValorAvaliacao(ValorAvaliacao valorAvaliacao) {
        this.valorAvaliacao = valorAvaliacao;
    }
}
