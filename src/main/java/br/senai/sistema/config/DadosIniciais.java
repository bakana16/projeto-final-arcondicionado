package br.senai.sistema.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Cliente;
import br.senai.sistema.model.Servico;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.ClienteRepository;
import br.senai.sistema.repository.ServicoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Cada bloco só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ServicoRepository servicoRepository, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (servicoRepository.count() == 0) {
            Servico servico1 = new Servico("Troca de óleo", "Troca de óleo e filtro", 30, new BigDecimal("120.00"));
            Servico servico2 = new Servico("Alinhamento e balanceamento", "Alinhamento das rodas dianteiras e balanceamento", 60, new BigDecimal("150.00"));
            Servico servico3 = new Servico("Revisão completa", "Revisão de 30 itens", 120, new BigDecimal("380.00"));
            servicoRepository.save(servico1);
            servicoRepository.save(servico2);
            servicoRepository.save(servico3);
        }

        if (clienteRepository.count() == 0) {
            Cliente cliente1 = new Cliente("Ana Paula Ramos", "(43) 99555-1010", "ana@email.com");
            Cliente cliente2 = new Cliente("Bruno Lima", "(43) 99666-2020", "bruno@email.com");
            Cliente cliente3 = new Cliente("Carla Nogueira", "(43) 99777-3030", "carla@email.com");
            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
        }
    }
}