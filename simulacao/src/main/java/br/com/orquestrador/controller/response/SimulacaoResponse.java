package br.com.orquestrador.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SimulacaoResponse {
    private Long idCliente;
    List<AgrupamentoResponse> agrupamentosList;
}
