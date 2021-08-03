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
public class SimulacaoParceladaPlanoResponse {
    private int quantidadeParcelasPlano;
    private BigDecimal valorTotalParcela;
    private BigDecimal percentualDescontoParcela;
    private BigDecimal valorDescontoParcela;
    private BigDecimal valorTotalParcelaComDesconto;
}
