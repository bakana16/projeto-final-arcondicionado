package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Servico;
import br.senai.sistema.repository.ServicoRepository;

/**
 * Camada de serviço de Servico: concentra as operações e, mais adiante, as regras de negócio.
 */
@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    /** Lista todos os registros, em ordem alfabética. */
    public List<Servico> listarTodos() {
        return servicoRepository.findAll(Sort.by("nome"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        servicoRepository.deleteById(id);
    }
}