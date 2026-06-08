package com.faculdade.gerenciador_academico.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.faculdade.gerenciador_academico.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // A palavra-chave secreta usada para trancar e destrancar o token.
    // O ideal é que ela fique no application.properties, mas deixamos um valor padrão por enquanto.
    @Value("${api.security.token.secret:senhasecreta123}")
    private String secret;

    // Método que gera o crachá quando o aluno faz login
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Gerenciador Academico")
                    .withSubject(usuario.getLogin()) // Guarda o e-mail do aluno dentro do token
                    .withExpiresAt(dataExpiracao())  // Define a validade do token
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    // Método que lê o crachá nas próximas requisições para ver quem é o aluno
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Gerenciador Academico")
                    .build()
                    .verify(tokenJWT)
                    .getSubject(); // Retorna o e-mail do aluno
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    // Define que o token vale por 2 horas a partir do momento do login (no fuso horário de Brasília)
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
