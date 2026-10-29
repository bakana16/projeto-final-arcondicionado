package br.senai.sistema.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.senai.sistema.model.Agendamento;
import br.senai.sistema.model.Indicador;
import br.senai.sistema.model.StatusAgendamento;
import br.senai.sistema.repository.AgendamentoRepository;

/**
 * Calcula os indicadores do painel (página inicial).
 * Usa consultas de contagem do Spring Data (count, countBy...) e laços (for) para contar e somar.
 */
@Service
public class PainelService {

    private final AgendamentoRepository agendamentoRepository;

    public PainelService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    /** Data de hoje no horário de Brasília (o Codespace usa o horário de Londres). */
    private LocalDate hoje() {
        return LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }

    /** Os indicadores do painel, na ordem em que aparecem. */
    public List<Indicador> indicadores() {
        long deHoje = agendamentoRepository.countByData(hoje());
        long proximos = agendamentoRepository.countByDataBetween(hoje().plusDays(1), hoje().plusDays(7));
        long concluidos = agendamentoRepository.countByStatus(StatusAgendamento.CONCLUIDO);
        long cancelados = agendamentoRepository.countByStatus(StatusAgendamento.CANCELADO);

        List<Indicador> lista = new ArrayList<>();
        lista.add(new Indicador("Hoje", String.valueOf(deHoje), "agendamentos para hoje", "bi-calendar-day", "primary"));
        lista.add(new Indicador("Próximos 7 dias", String.valueOf(proximos), "a partir de amanhã", "bi-calendar-week", "secondary"));
        lista.add(new Indicador("Concluídos", String.valueOf(concluidos), "atendimentos realizados", "bi-check2-circle", "success"));
        lista.add(new Indicador("Cancelados", String.valueOf(cancelados), "horários liberados", "bi-x-circle", "danger"));
        return lista;
    }

    /** Lista de atenção: os agendamentos de hoje, em ordem de horário. */
    public List<Agendamento> atencao() {
        return agendamentoRepository.findByDataOrderByHorarioAsc(hoje());
    }

    /** Gráfico: nomes das barras (uma por situação). */
    public List<String> graficoRotulos() {
        List<String> rotulos = new ArrayList<>();
        for (StatusAgendamento status : StatusAgendamento.values()) {
            rotulos.add(status.getDescricao());
        }
        return rotulos;
    }

    /** Gráfico: altura de cada barra (quantos agendamentos em cada situação). */
    public List<Long> graficoValores() {
        List<Long> valores = new ArrayList<>();
        for (StatusAgendamento status : StatusAgendamento.values()) {
            valores.add(agendamentoRepository.countByStatus(status));
        }
        return valores;
    }
}