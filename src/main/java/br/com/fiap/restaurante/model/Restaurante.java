package br.com.fiap.restaurante.model;

import br.com.fiap.restaurante.model.context.DiasFuncionamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonBackReference
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonBackReference
    private Set<Reserva> reservas = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "especialidade_id")
    @JsonManagedReference
    private Especialidade especialidade;

    @Column(name = "capacidade_pessoas")
    private Integer capacidadePessoas;

    @Column(name = "nome")
    private String nome;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "endereco_completo")
    private String enderecoCompleto;

    @Column(name = "horario_abertura")
    private LocalTime horarioAbertura;

    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento;

    @Column(name = "tolerancia")
    private LocalTime tolerancia;

    @Column(name = "dias_tolerancia")
    private Set<DiasFuncionamento> diasTolerancia;

    public Restaurante() {
    }

    public Restaurante(
            Set<Avaliacao> avaliacoes,
            Set<Reserva> reservas,
            Especialidade especialidade,
            Integer capacidadePessoas,
            String nome,
            Integer latitude,
            Integer longitude,
            String enderecoCompleto,
            LocalTime horarioAbertura,
            LocalTime horarioFechamento,
            LocalTime tolerancia,
            Set<DiasFuncionamento> diasTolerancia
    ) {
        this.avaliacoes = avaliacoes;
        this.reservas = reservas;
        this.especialidade = especialidade;
        this.capacidadePessoas = capacidadePessoas;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.enderecoCompleto = enderecoCompleto;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.tolerancia = tolerancia;
        this.diasTolerancia = diasTolerancia;
    }

    public Set<DiasFuncionamento> getDiasTolerancia() {
        return diasTolerancia;
    }

    public void setDiasTolerancia(Set<DiasFuncionamento> diasTolerancia) {
        this.diasTolerancia = diasTolerancia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Set<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getCapacidadePessoas() {
        return capacidadePessoas;
    }

    public void setCapacidadePessoas(Integer capacidadePessoas) {
        this.capacidadePessoas = capacidadePessoas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public LocalTime getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(LocalTime horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public LocalTime getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(LocalTime tolerancia) {
        this.tolerancia = tolerancia;
    }
}
