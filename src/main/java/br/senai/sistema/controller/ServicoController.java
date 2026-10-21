package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.sistema.service.ServicoService;

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
}