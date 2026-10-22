package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Agendamento;

/**
 * Acesso ao banco de dados para a entidade Agendamento.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Agendamento, Long>  ->  entidade Agendamento, chave primária do tipo Long.
 */
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}