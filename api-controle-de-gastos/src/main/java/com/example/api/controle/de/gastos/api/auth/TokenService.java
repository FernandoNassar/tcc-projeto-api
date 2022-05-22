package com.example.api.controle.de.gastos.api.auth;

import com.example.api.controle.de.gastos.api.services.UsuarioService;
import com.example.api.controle.de.gastos.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    public static final long TOKEN_EXPIRATION = 86400000L;


    public String buildToken(Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        return Jwts.builder()
                .setIssuer("API controle-de-gastos")
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        token = tokenFormatter(token);
        var claims = Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Usuario getUsuario(String token) {
        token = tokenFormatter(token);
        return usuarioService.findByUsername(getUsername(token));
    }

    public String getToken(AuthenticationManager authManager, String username, String password) {
       var credential = new UsernamePasswordAuthenticationToken(username, password);
       Authentication authentication = authManager.authenticate(credential);
       return buildToken(authentication);
    }

    public String getToken(AuthenticationManager authManager, Usuario usuario, String password) {
        if(!isPasswordValid(password, usuario))
            throw new RuntimeException("As senhas não coincidem");
        var credential = new UsernamePasswordAuthenticationToken(usuario.getUsername(), password);
        var authentication = authManager.authenticate(credential);
        return buildToken(authentication);
    }

    private String tokenFormatter(String token) {
        return token.replace("Bearer", "").trim();
    }

    private boolean isPasswordValid(String password, Usuario usuario) {
        return bCryptPasswordEncoder.matches(password, usuario.getPassword());
    }
}
