package br.com.orquestrador.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AgrupamentoResponse {
    private String tipoAgrupamentoPrincipal;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotalSemDesconto;
    private BigDecimal valorTotalComDesconto;
    private List<SimulacaoParceladaPlanoResponse> planosList;
    private List<SimulacaoParceladaContratoResponse> contratosList;
}
