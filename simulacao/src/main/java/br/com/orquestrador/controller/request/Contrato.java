package br.com.orquestrador.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {
    private Long numeroContrato;
    private Long codigoProduto;
    private BigDecimal valorContrato;
    private Integer qtdeDiasAtraso;
}
