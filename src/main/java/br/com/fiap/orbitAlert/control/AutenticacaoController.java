package br.com.fiap.orbitAlert.control;

import br.com.fiap.orbitAlert.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/login")
    public String logar(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "duracao", defaultValue = "60") Integer duracao) {
        try {
            var autenticacao = new UsernamePasswordAuthenticationToken(email, password);
            manager.authenticate(autenticacao);
            return jwtUtil.gerarToken(email, duracao);
        } catch (Exception e) {
            return "Credenciais inválidas!";
        }
    }
}
