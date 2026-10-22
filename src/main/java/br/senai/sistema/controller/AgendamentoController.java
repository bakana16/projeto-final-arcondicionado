package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Agendamento;
import br.senai.sistema.service.AgendamentoService;
import br.senai.sistema.service.ClienteService;
import br.senai.sistema.service.ServicoService;
import jakarta.validation.Valid;

/**
 * Controller de Agendamento: recebe as requisições do navegador para /agendamentos.
 */
@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final ClienteService clienteService;
    private final ServicoService servicoService;

    public AgendamentoController(AgendamentoService agendamentoService,
                                  ClienteService clienteService,
                                  ServicoService servicoService) {
        this.agendamentoService = agendamentoService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
    }

    /** GET /agendamentos -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarTodos());
        return "agendamentos/lista";
    }

    /** GET /agendamentos/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        carregarListas(model);
        return "agendamentos/form";
    }

    /**
     * POST /agendamentos/salvar -> recebe o formulário (cadastro ou edição).
     * @Valid confere as regras da entidade; os erros ficam em "resultado".
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("agendamento") Agendamento agendamento, BindingResult resultado,
                         Model model, RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            carregarListas(model);
            return "agendamentos/form";   // volta para o formulário mostrando os erros
        }
        agendamentoService.salvar(agendamento);
        atributos.addFlashAttribute("mensagem", "Agendamento salvo com sucesso.");
        return "redirect:/agendamentos";
    }

    /** GET /agendamentos/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("agendamento", agendamentoService.buscarPorId(id));
        carregarListas(model);
        return "agendamentos/form";
    }

    /** POST /agendamentos/5/excluir -> exclui o registro de id 5. */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        agendamentoService.excluir(id);
        atributos.addFlashAttribute("mensagem", "Agendamento excluído com sucesso.");
        return "redirect:/agendamentos";
    }

    /** Coloca no model as listas usadas nas listas de escolha (select) do formulário. */
    private void carregarListas(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
    }
}