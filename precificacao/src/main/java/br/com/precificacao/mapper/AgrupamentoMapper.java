package br.com.precificacao.mapper;

import br.com.precificacao.AgrupamentoClusterRequest;
import br.com.precificacao.domain.Agrupamento;
import br.com.precificacao.domain.Contrato;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AgrupamentoMapper {
    Agrupamento toAgrupamento(final AgrupamentoClusterRequest agrupamento,
                              final BigDecimal valorTotal,
                              final BigDecimal valorJurosTotal,
                              final List<Contrato> contratos);

}
