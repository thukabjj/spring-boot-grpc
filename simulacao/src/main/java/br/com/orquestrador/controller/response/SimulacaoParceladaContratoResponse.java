package br.com.orquestrador.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SimulacaoParceladaContratoResponse {
    private Long numeroContrato;
    private Long codigoProduto;
    private BigDecimal valorContratoSemJuros;
    private BigDecimal percentualJurosMes;
    private BigDecimal valorJuros;
    private BigDecimal valorTotalContrato;
}
