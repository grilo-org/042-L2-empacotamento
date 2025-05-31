package com.l2.empacotamento.controller;

import com.l2.empacotamento.dto.ProcessarEmpacotamentoDto;
import com.l2.empacotamento.service.EmpacotamentoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final EmpacotamentoService empacotamentoService;

    public PedidoController(EmpacotamentoService empacotamentoService) {
        this.empacotamentoService = empacotamentoService;
    }

    @PostMapping("/processar-empacotamento")
    @Operation(summary = "Processa o empacotamento de uma lista de produtos")
    public ResponseEntity<ProcessarEmpacotamentoDto> processarEmpacotamento(
            @RequestBody ProcessarEmpacotamentoDto processarEmpacotamentoDto
    ) {
        return ResponseEntity.ok(this.empacotamentoService.processarEmpacotamento(processarEmpacotamentoDto));
    }
}
