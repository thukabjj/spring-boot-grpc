package br.com.orquestrador.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoRequest {
    private Long idCliente;
    private List<Contrato> contratos;
}
