package br.senai.sistema.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Entidade Servico: representa o serviço que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "servicos" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 */
@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome do serviço oferecido. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 80, message = "Use no máximo 80 caracteres.")
    @Column(nullable = false, length = 80)
    private String nome;

    /** Detalhes do serviço. */
    @Size(max = 255, message = "Use no máximo 255 caracteres.")
    @Column(length = 255)
    private String descricao;

    /** Tempo necessário, em minutos. */
    @NotNull(message = "Campo obrigatório.")
    @PositiveOrZero(message = "O valor não pode ser negativo.")
    @Column(nullable = false)
    private Integer duracaoMinutos;

    /** Valor cobrado. */
    @NotNull(message = "Campo obrigatório.")
    @DecimalMin(value = "0.00", message = "O valor não pode ser negativo.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Servico() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Servico(String nome, String descricao, Integer duracaoMinutos, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.duracaoMinutos = duracaoMinutos;
        this.preco = preco;
    }

    // ---- Getters e setters ----

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