package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.auth.TokenService;
import com.example.api.controle.de.gastos.api.dto.auth.CadastroReq;
import com.example.api.controle.de.gastos.api.dto.auth.CadastroResp;
import com.example.api.controle.de.gastos.api.dto.auth.LoginReq;
import com.example.api.controle.de.gastos.api.dto.auth.TokenRes;
import com.example.api.controle.de.gastos.api.services.UsuarioService;
import com.example.api.controle.de.gastos.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/login")
    public ResponseEntity<TokenRes> autenticar(@RequestBody @Valid LoginReq requestBody) {
        try {
            var usuario = usuarioService.findByUsername(requestBody.getUsername());
            var token = tokenService.getToken(authManager, usuario, requestBody.getPassword());
            return ResponseEntity.ok(new TokenRes("bearer", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/cadastrar")
    public ResponseEntity<CadastroResp> cadastrar (@RequestBody @Valid CadastroReq reqBody, HttpServletRequest request)
            throws URISyntaxException {

        var usuario = modelMapper.map(reqBody, Usuario.class);
        usuario = usuarioService.save(usuario);
        var location = new URI(request.getContextPath());

        try {
            var token = tokenService.getToken(authManager, usuario, reqBody.getPassword());
            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            return ResponseEntity.created(location).headers(headers).body(modelMapper.map(usuario, CadastroResp.class));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
