package com.example.api.controle.de.gastos.api.auth;

import com.example.api.controle.de.gastos.api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoPorToken extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AutenticacaoPorToken(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = getToken(request);
        if (tokenService.isValid(token)) authenticate(token);
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer"))
            return null;
        return token.substring(7, token.length());
    }

    private void authenticate(String token) {
        var username = tokenService.getUsername(token);
        var usuario = usuarioService.findByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
