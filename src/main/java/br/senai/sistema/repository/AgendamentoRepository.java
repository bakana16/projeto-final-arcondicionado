package br.senai.sistema.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Agendamento;
import br.senai.sistema.model.StatusAgendamento;

/**
 * Acesso ao banco de dados para a entidade Agendamento.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Agendamento, Long>  ->  entidade Agendamento, chave primária do tipo Long.
 */
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Regra de negócio: agendamentos na mesma data e horário (para conferir se o horário está livre)
    List<Agendamento> findByDataAndHorario(LocalDate data, LocalTime horario);

    // Filtro: registros de uma situação, na ordem indicada
    List<Agendamento> findByStatus(StatusAgendamento status, Sort ordem);
}