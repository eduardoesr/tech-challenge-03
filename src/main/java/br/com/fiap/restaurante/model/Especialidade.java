package br.com.fiap.restaurante.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_especialidade")
public class Especialidade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "especialidade", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonBackReference
    private Set<Restaurante> restaurantes = new HashSet<>();

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    public Especialidade() {
    }

    public Especialidade(String descricao, String nome, Set<Restaurante> restaurantes) {
        this.descricao = descricao;
        this.nome = nome;
        this.restaurantes = restaurantes;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
