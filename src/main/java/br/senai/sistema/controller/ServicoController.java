package br.senai.sistema.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Servico;
import br.senai.sistema.service.ServicoService;
import jakarta.validation.Valid;

/**
 * Controller de Servico: recebe as requisições do navegador para /servicos.
 */
@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    /** GET /servicos -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicos", servicoService.listarTodos());
        return "servicos/lista";
    }

    /** GET /servicos/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("servico", new Servico());
        return "servicos/form";
    }

    /**
     * POST /servicos/salvar -> recebe o formulário (cadastro ou edição).
     * @Valid confere as regras da entidade; os erros ficam em "resultado".
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("servico") Servico servico, BindingResult resultado,
                         RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            return "servicos/form";   // volta para o formulário mostrando os erros
        }
        servicoService.salvar(servico);
        atributos.addFlashAttribute("mensagem", "Serviço salvo com sucesso.");
        return "redirect:/servicos";
    }

    /** GET /servicos/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("servico", servicoService.buscarPorId(id));
        return "servicos/form";
    }

    /**
     * POST /servicos/5/excluir -> exclui o registro de id 5.
     * Se houver registros ligados a ele, o banco recusa a exclusão (integridade referencial)
     * e o sistema mostra um aviso, em vez de uma página de erro.
     */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            servicoService.excluir(id);
            atributos.addFlashAttribute("mensagem", "Serviço excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            atributos.addFlashAttribute("erro", "Não é possível excluir: existem agendamentos ligados a este serviço.");
        }
        return "redirect:/servicos";
    }
}