package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Agendamento;
import br.senai.sistema.repository.AgendamentoRepository;

/**
 * Camada de serviço de Agendamento: concentra as operações e, mais adiante, as regras de negócio.
 */
@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    /** Lista todos os registros, em ordem de data e horário. */
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll(Sort.by("data", "horario"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        agendamentoRepository.deleteById(id);
    }
}