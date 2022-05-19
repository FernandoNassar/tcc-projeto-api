package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.auth.TokenService;
import com.example.api.controle.de.gastos.api.dto.resumo.ResumoMes;
import com.example.api.controle.de.gastos.api.services.ResumoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/resumo")
public class ResumoResource {

    private final ResumoService resumoService;
    private final TokenService tokenService;

    public ResumoResource(ResumoService resumoService, TokenService tokenService) {
        this.resumoService = resumoService;
        this.tokenService = tokenService;
    }


    @GetMapping(path = "/{ano}/{mes}")
    public ResumoMes resumoPorMesEAno(
            @PathVariable("mes") Integer mes,
            @PathVariable("ano") Integer ano,
            @RequestHeader("Authorization") String token) {
        return resumoService.getResumoMes(mes, ano, tokenService.getUsuario(token));
    }
}
