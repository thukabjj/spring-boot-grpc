package br.com.orquestrador.domain.agrupamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AgrupamentoCluster {
    private String tipoAgrupamento;
    private List<ContratoCluster> contratosList;
}
