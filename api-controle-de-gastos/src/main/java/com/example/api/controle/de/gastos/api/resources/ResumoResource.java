package com.example.api.controle.de.gastos.api.resources;

import com.example.api.controle.de.gastos.api.dto.resumo.ResumoMes;
import com.example.api.controle.de.gastos.api.services.ResumoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/resumo")
public class ResumoResource {

    private final ResumoService resumoService;

    public ResumoResource(ResumoService resumoService) {
        this.resumoService = resumoService;
    }


    @GetMapping(path = "/{ano}/{mes}")
    public ResumoMes resumoPorMesEAno(@PathVariable("mes") Integer mes, @PathVariable("ano") Integer ano) {
        return resumoService.getResumoMes(mes, ano);
    }
}
