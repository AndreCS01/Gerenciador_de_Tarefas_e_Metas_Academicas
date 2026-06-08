package com.faculdade.gerenciador_academico.ui;

import com.faculdade.gerenciador_academico.domain.Usuario;
import com.faculdade.gerenciador_academico.infra.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody DadosCadastroUsuario dados) {
        // 1. Valida se o e-mail/login já está sendo usado por outro aluno
        if (repository.findByLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Erro: Este login já está cadastrado no sistema.");
        }

        // 2. Cria a nova entidade
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dados.login());
        
        // 3. Transforma a senha em texto limpo em um Hash BCrypt seguro
        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        novoUsuario.setSenha(senhaCriptografada);

        // 4. Salva no PostgreSQL
        repository.save(novoUsuario);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    // DTO para receber os dados de cadastro do novo aluno
    public record DadosCadastroUsuario(String login, String senha) {}
}
