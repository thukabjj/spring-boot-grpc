package br.com.orquestrador.domain.agrupamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ContratoCluster {
    private Long numeroContrato;
    private Long codigoProduto;
    private BigDecimal valorContrato;
    private int qtdeDiasAtraso;
}
