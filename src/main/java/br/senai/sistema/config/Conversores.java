package br.senai.sistema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.model.Servico;
import br.senai.sistema.repository.ClienteRepository;
import br.senai.sistema.repository.ServicoRepository;

/**
 * Conversores: transformam o id que vem do formulário (um texto, por exemplo "3")
 * no objeto correspondente do banco. São usados pelas listas de escolha (select)
 * dos formulários que têm relacionamento.
 */
@Configuration
public class Conversores implements WebMvcConfigurer {

    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    public Conversores(ClienteRepository clienteRepository,
                       ServicoRepository servicoRepository) {
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // "3" -> cliente de id 3 (texto vazio -> nenhum, e a validação avisa se for obrigatório)
        registry.addConverter(String.class, Cliente.class,
                id -> id.isBlank() ? null : clienteRepository.findById(Long.valueOf(id)).orElse(null));

        // "3" -> serviço de id 3 (texto vazio -> nenhum, e a validação avisa se for obrigatório)
        registry.addConverter(String.class, Servico.class,
                id -> id.isBlank() ? null : servicoRepository.findById(Long.valueOf(id)).orElse(null));
    }
}