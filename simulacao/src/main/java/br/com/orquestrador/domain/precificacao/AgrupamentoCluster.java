package br.com.orquestrador.domain.precificacao;

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
public class AgrupamentoCluster {
    private String tipoAgrupamento;
    private BigDecimal valorTotal;
    private BigDecimal valorJurosTotal ;
    private List<ContratoCluster> contratosList;
}
