package br.com.simulacaoparcelada.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoParcelada {
    private Long idCliente;
    private String tipoAgrupamentoPrincipal;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotalSemDesconto;
    private BigDecimal valorTotalComDesconto;
    private List<SimulacaoParceladaPlano> planos;
    private List<SimulacaoParceladaContrato> contratos;
}
