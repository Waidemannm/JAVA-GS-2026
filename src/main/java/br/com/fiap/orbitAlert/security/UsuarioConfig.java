package br.com.fiap.orbitAlert.security;

import br.com.fiap.orbitAlert.model.Usuario;
import br.com.fiap.orbitAlert.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService detailsService() {
        return email -> {
            Usuario usuario = usuarioRepository.findByDsEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Usuário não encontrado com e-mail: " + email));
            return User.builder()
                    .username(usuario.getDsEmail())
                    .password(usuario.getDsSenhaHash())
                    .roles(usuario.getTpPerfil().name())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
