package br.senai.sistema.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade Servico: representa o servico que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "servicos" no banco.
 * Cada atributo vira UMA COLUNA dessa tabela.
 */
@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome do serviço oferecido. */
    @Column(nullable = false, length = 80)
    private String nome;

    /** Detalhes do serviço. */
    @Column(length = 255)
    private String descricao;

    /** Tempo necessário, em minutos. */
    @Column(nullable = false)
    private Integer duracaoMinutos;

    /** Valor cobrado. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /** Construtor vazio: obrigatório para o JPA criar objetos ao ler do banco. */
    public Servico() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Servico(String nome, String descricao, Integer duracaoMinutos, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.duracaoMinutos = duracaoMinutos;
        this.preco = preco;
    }

    // ---- Getters e setters: a forma de ler e alterar os atributos privados ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}