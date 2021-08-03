package br.com.orquestrador.controller;

import br.com.orquestrador.controller.request.SimulacaoRequest;
import br.com.orquestrador.controller.response.SimulacaoResponse;
import br.com.orquestrador.simulacao.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimulacaoController {

    @Autowired
    private SimulacaoService service;

    @PostMapping("simulacao_parcelada")
    public ResponseEntity<SimulacaoResponse> orquestradorSimulacaoParcelada(@RequestBody SimulacaoRequest request) {
        SimulacaoResponse response = service.simulacaoParcelada(request);
        return ResponseEntity.ok(response);
    }
}
