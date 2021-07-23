package br.com.simulacaoparcelada.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum PlanosEnum {
    OITO_PARCELAS(8L),
    VINTE_QUATRO_PARCELAS(24L),
    QUARENTA_OITO_PARCELAS(48L),
    SESSENTA_PARCELAS(60L);

    private Long quantidadeParcelas;
}
