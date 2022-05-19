package com.example.api.controle.de.gastos.api.services;

import com.example.api.controle.de.gastos.api.exceptions.ResourceNotFoundException;
import com.example.api.controle.de.gastos.entities.Usuario;
import com.example.api.controle.de.gastos.repositories.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Usuario save(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(username)));
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getErrorMessage(id)));
    }

    private String getErrorMessage(String username) {
        return "Usuário (username: " + username + ") not found";
    }
    private String getErrorMessage(Integer id) {
        return "Usuário (id: " + id + ") not found";
    }
}
