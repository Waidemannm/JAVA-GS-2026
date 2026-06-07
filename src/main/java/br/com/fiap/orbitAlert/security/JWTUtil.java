package br.com.fiap.orbitAlert.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey CHAVE = Jwts.SIG.HS256.key().build();

    public String gerarToken(String email, Integer duracao) {
        Date dataAtual = new Date();
        JwtBuilder builder = Jwts.builder()
                .subject(email)
                .issuedAt(dataAtual)
                .expiration(new Date(dataAtual.getTime() + (1000L * 60 * duracao)))
                .signWith(CHAVE);
        return builder.compact();
    }

    public String extrairEmail(String token) {
        JwtParser parser = Jwts.parser().verifyWith(CHAVE).build();
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validarToken(String token) {
        try {
            JwtParser parser = Jwts.parser().verifyWith(CHAVE).build();
            parser.parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
