package br.com.simulacaoparcelada.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoParceladaPlano {
    private Long quantidadeParcelasPlano;
    private BigDecimal valorTotalParcela;
    private BigDecimal percentualDescontoParcela;
    private BigDecimal valorDescontoParcela;
    private BigDecimal valorTotalParcelaComDesconto;
}
