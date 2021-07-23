package br.com.precificacao.domain;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Contrato {
    private Long numeroContrato;
    private Long codigoProduto;
    private BigDecimal valorContratoSemJuros;
    private BigDecimal percentualJurosMes;
    private BigDecimal valorJuros;
    private BigDecimal valorTotalContrato;
}
