package br.com.orquestrador.domain.precificacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Precificacao {
    private Long idCliente;
    private String tipoAgrupamentoPrincipal;
    private List<AgrupamentoCluster> agrupamentosList;
}
