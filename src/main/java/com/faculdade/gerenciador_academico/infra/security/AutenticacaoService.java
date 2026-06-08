package com.faculdade.gerenciador_academico.infra.security;

import com.faculdade.gerenciador_academico.infra.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco pelo e-mail (login)
        UserDetails usuario = repository.findByLogin(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o login: " + username);
        }
        
        return usuario;
    }
}
