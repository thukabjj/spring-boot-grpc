package br.com.precificacao.mapper;

import br.com.precificacao.ContratoClusterRequest;
import br.com.precificacao.domain.Contrato;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.math.BigDecimal;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ContratoMapper {

    @Mapping(target = "valorContratoSemJuros",source = "contrato.valorContrato")
    Contrato toContrato(ContratoClusterRequest contrato,
                        BigDecimal valorTotalContrato,
                        BigDecimal valorJuros,
                        BigDecimal percentualJurosMes);
}
