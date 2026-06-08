package com.faculdade.gerenciador_academico.infra;

import com.faculdade.gerenciador_academico.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // O Spring Security usa esse método específico para buscar o usuário pelo login (e-mail)
    UserDetails findByLogin(String login);
}
