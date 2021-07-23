package br.com.precificacao.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Agrupamento {
    private String tipoAgrupamento;
    private BigDecimal valorTotal;
    private BigDecimal valorJurosTotal;
    private List<Contrato> contratos;

    public int getContratosCount (){
        return this.contratos.size();
    }
}
