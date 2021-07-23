package br.com.agrupamento.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum ProdutoAgrupamentoEnum {

    FINANCIAMENTO("A",new Long[]{5010L,5020L,5030L}),
    EMPRESTIMO("B",new Long[]{4010L,4020L,4030L}),
    CARTAO_CREDITO("C",new Long[]{3010L,3020L,3030L});

    private String tipoAgrupamento;
    private Long []codigosProduto;

    public static synchronized ProdutoAgrupamentoEnum getTipoAgrupamentoProduto(final Long codigoProduto) {
        final Optional<ProdutoAgrupamentoEnum> optionalProduto = Arrays.stream(ProdutoAgrupamentoEnum.values())
                .filter(c -> Arrays.stream(c.codigosProduto).filter(cp -> cp.equals(codigoProduto)).findFirst().isPresent())
                .findFirst();
            return optionalProduto.orElseThrow( () -> new IllegalArgumentException("Produto não pode ser agrupado"));
    }
}
