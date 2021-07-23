package br.com.simulacaoparcelada.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoParceladaContrato {
    private Long numeroContrato;
    private Long codigoProduto;
    private BigDecimal valorContratoSemJuros;
    private BigDecimal percentualJurosMes;
    private BigDecimal valorJuros;
    private BigDecimal valorTotalContrato;
}
