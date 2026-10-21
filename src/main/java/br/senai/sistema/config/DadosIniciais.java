package br.senai.sistema.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Servico;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.ServicoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ServicoRepository servicoRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         ServicoRepository servicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.servicoRepository = servicoRepository;
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
    }
}