package br.senai.sistema.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sistema.model.Agendamento;
import br.senai.sistema.model.StatusAgendamento;
import br.senai.sistema.repository.AgendamentoRepository;

/**
 * Camada de serviço de Agendamento: concentra as operações e as REGRAS DE NEGÓCIO.
 * Quando uma regra não é cumprida, o método lança RegraNegocioException com a mensagem para o usuário.
 */
@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    // Injeção de dependência: o Spring entrega os repositórios prontos pelo construtor.
    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    /** Lista todos os registros, em ordem de data e horário. */
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll(Sort.by("data", "horario"));
    }

    /** Lista os registros de uma situação; sem filtro (null), lista todos. */
    public List<Agendamento> listar(StatusAgendamento status) {
        if (status == null) {
            return listarTodos();
        }
        return agendamentoRepository.findByStatus(status, Sort.by("data", "horario"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
    }

    /**
     * Salva, conferindo as regras de negócio. Agendamento novo começa AGENDADO;
     * na edição, a situação não muda pelo formulário.
     */
    @Transactional
    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            agendamento.setStatus(StatusAgendamento.AGENDADO);
            verificarSeNaoPassou(agendamento);                                   // regra 2
        } else {
            Agendamento atual = buscarPorId(agendamento.getId());
            agendamento.setStatus(atual.getStatus());
            boolean mudouHorario = !atual.getData().equals(agendamento.getData())
                    || !atual.getHorario().equals(agendamento.getHorario());
            if (mudouHorario) {
                verificarSeNaoPassou(agendamento);                               // regra 2
            }
        }
        verificarHorarioLivre(agendamento);                                      // regra 1
        return agendamentoRepository.save(agendamento);
    }

    /** Muda a situação (Concluir ou Cancelar). Só um agendamento AGENDADO pode mudar. */
    @Transactional
    public void mudarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = buscarPorId(id);
        if (agendamento.getStatus() != StatusAgendamento.AGENDADO) {
            throw new RegraNegocioException("Só é possível alterar a situação de um agendamento que está Agendado.");
        }
        agendamento.setStatus(novoStatus);
        agendamentoRepository.save(agendamento);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        agendamentoRepository.deleteById(id);
    }

    /** Regra de negócio 1: não pode haver dois agendamentos na mesma data e horário (cancelados não contam). */
    private void verificarHorarioLivre(Agendamento agendamento) {
        List<Agendamento> mesmoHorario = agendamentoRepository.findByDataAndHorario(agendamento.getData(), agendamento.getHorario());
        for (Agendamento outro : mesmoHorario) {
            boolean outroRegistro = !outro.getId().equals(agendamento.getId());
            if (outroRegistro && outro.getStatus() != StatusAgendamento.CANCELADO) {
                throw new RegraNegocioException("Já existe um agendamento nesta data e horário. Escolha outro horário.");
            }
        }
    }

    /** Regra de negócio 2: não agendar em data e horário que já passaram (no horário de Brasília). */
    private void verificarSeNaoPassou(Agendamento agendamento) {
        LocalDateTime quando = LocalDateTime.of(agendamento.getData(), agendamento.getHorario());
        LocalDateTime agora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        if (quando.isBefore(agora)) {
            throw new RegraNegocioException("Não é possível agendar em data e horário que já passaram.");
        }
    }
}