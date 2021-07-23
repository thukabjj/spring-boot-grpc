package br.com.simulacaoparcelada.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum DescontoAgrupamentoEnum {

    PERCENTUA_DESCONTO_FINANCIAMENTO("A", new BigDecimal(10)),
    PERCENTUA_DESCONTO_EMPRESTIMO("B",new BigDecimal(20)),
    PERCENTUA_DESCONTO_CARTAO_CREDITO("C",new BigDecimal(30));

    private String tipoAgrupamento;
    private BigDecimal percentualDesconto;

    public static synchronized DescontoAgrupamentoEnum getDescontoAgrupamento(final String tipoAgrupamento) {
        final Optional<DescontoAgrupamentoEnum> percentualDescont = Arrays.stream(DescontoAgrupamentoEnum.values())
                .filter(c -> c.getTipoAgrupamento().equals(tipoAgrupamento))
                .findFirst();
        return percentualDescont.orElseThrow( () -> new IllegalArgumentException("O agrupamento não possui desconto cadastrado!"));
    }
}
