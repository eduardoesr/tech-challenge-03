package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.model.context.StatusReserva;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurante_id")
    @JsonManagedReference
    private Restaurante restaurante;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "quantidade_pessoas")
    private Integer quantidadePessoas;

    @Column(name = "data_reserva")
    private LocalDateTime dataReserva;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_reserva")
    private StatusReserva statusReserva;

    public Reserva(
            String nomeCliente,
            Integer quantidadePessoas,
            LocalDateTime dataReserva) {
        this.nomeCliente = nomeCliente;
        this.quantidadePessoas = quantidadePessoas;
        this.dataReserva = dataReserva;
        this.statusReserva = StatusReserva.PENDENTE;
    }

    public Reserva() {

    }

    public Long getId() {
        return id;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }
}
