package br.com.precificacao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum TaxaProdutoEnum {
    FINANCIAMENTO(new BigDecimal(5.39),new Long[]{5010L,5020L,5030L}),
    EMPRESTIMO(new BigDecimal(2.84),new Long[]{4010L,4020L,4030L}),
    CARTAO_CREDITO(new BigDecimal(15.00),new Long[]{3010L,3020L,3030L});

    private BigDecimal taxaJurosMesProduto;
    private Long []codigosProduto;

    public static synchronized TaxaProdutoEnum getTaxaJurosProduto(final Long codigoProduto) {
        final Optional<TaxaProdutoEnum> optionalProduto = Arrays.stream(TaxaProdutoEnum.values())
                .filter(c -> Arrays.stream(c.codigosProduto).filter(cp -> cp.equals(codigoProduto)).findFirst().isPresent())
                .findFirst();
        return optionalProduto.orElseThrow( () -> new IllegalArgumentException("Produto não possui taxa cadastrada"));
    }


}
