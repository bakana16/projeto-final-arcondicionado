package br.senai.sistema.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidade Agendamento: representa o agendamento que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "agendamentos" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 * Os atributos com @ManyToOne guardam, no banco, só o id do registro relacionado (chave estrangeira).
 */
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Dia do atendimento. */
    @NotNull(message = "Campo obrigatório.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate data;

    /** Horário do atendimento. */
    @NotNull(message = "Campo obrigatório.")
    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime horario;

    /** Observação (opcional). */
    @Size(max = 255, message = "Use no máximo 255 caracteres.")
    @Column(length = 255)
    private String observacao;

    /** Cliente atendido: relacionamento N:1 (muitos registros daqui apontam para um Cliente). */
    @NotNull(message = "Selecione um cliente.")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /** Serviço agendado: relacionamento N:1 (muitos registros daqui apontam para um Servico). */
    @NotNull(message = "Selecione um serviço.")
    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Agendamento() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Agendamento(LocalDate data, LocalTime horario, Cliente cliente, Servico servico) {
        this.data = data;
        this.horario = horario;
        this.cliente = cliente;
        this.servico = servico;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}