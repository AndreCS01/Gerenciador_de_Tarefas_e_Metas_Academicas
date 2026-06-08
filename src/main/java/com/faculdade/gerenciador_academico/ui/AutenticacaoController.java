package com.faculdade.gerenciador_academico.ui;

import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody DadosAutenticacao dados) {
        // 1. Converte o DTO de entrada para o token de autenticação do Spring
        UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // 2. Dispara a validação (chama o AutenticacaoService e valida a senha criptografada)
        Authentication authentication = manager.authenticate(authenticationToken);

        // 3. Se passar, gera o token JWT para o usuário autenticado
        String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // 4. Retorna o token encapsulado em um objeto JSON de resposta
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    // DTO para receber as credenciais da requisição
    public record DadosAutenticacao(String login, String senha) {}

    // DTO para devolver o token estruturado no formato JSON
    public record DadosTokenJWT(String token) {}
}
