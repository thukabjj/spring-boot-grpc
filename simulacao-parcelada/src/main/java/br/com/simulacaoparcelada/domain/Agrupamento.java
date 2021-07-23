package br.com.simulacaoparcelada.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Agrupamento {
    private String tipoAgrupamentoPrincipal;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotalSemDesconto;
    private BigDecimal valorTotalComDesconto;
    private List<SimulacaoParceladaPlano> planos;
    private List<SimulacaoParceladaContrato> contratos;
}
