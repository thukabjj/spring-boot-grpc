package br.com.simulacaoparcelada.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum ValorMinimoPlanoEnum {
    VALOR_MINIMO_PLANO_50_REAIS(BigDecimal.valueOf(50));
    private BigDecimal value;
}
