package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Servico;
import br.senai.sistema.repository.ServicoRepository;

/**
 * Camada de serviço de Servico: é aqui que ficarão as regras de negócio.
 * Por enquanto, ela só busca os dados no repositório.
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
}