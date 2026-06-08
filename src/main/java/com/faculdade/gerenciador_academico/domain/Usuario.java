package com.faculdade.gerenciador_academico.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O login não pode se repetir no banco (unique = true)
    @Column(unique = true, nullable = false)
    private String login; 

    @Column(nullable = false)
    private String senha;

    // Getters e Setters normais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public void setSenha(String senha) { this.senha = senha; }

    // --- MÉTODOS OBRIGATÓRIOS DO CONTRATO DO SPRING SECURITY ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Para simplificar, todo aluno que se cadastrar terá o perfil de usuário comum
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login; // O Spring usa a palavra "Username", mas no nosso caso é o campo "login"
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta não expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta não bloqueia
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Senha não expira
    }

    @Override
    public boolean isEnabled() {
        return true; // Conta vem ativada por padrão
    }
}
