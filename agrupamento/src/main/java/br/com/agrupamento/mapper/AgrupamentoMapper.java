package br.com.agrupamento.mapper;

import br.com.agrupamento.AgrupamentoClusterResponse;
import br.com.agrupamento.AgrupamentoResponse;
import br.com.agrupamento.ContratoClusterResponse;
import br.com.agrupamento.ContratoRequest;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AgrupamentoMapper {

    @Mapping(target = "allFields",ignore = true)
    ContratoClusterResponse toContratoClusterResponse(ContratoRequest contratoRequest);
    List<ContratoClusterResponse> toContratoClusterResponseList(List<ContratoRequest> contratoRequestList);

    @Mapping(target = "allFields",ignore = true)
    AgrupamentoClusterResponse toAgrupamentoClusterResponse(String tipoAgrupamento, List<ContratoClusterResponse> contratosList);

    AgrupamentoResponse toAgrupamentoResponse(Long idCliente, String tipoAgrupamentoPrincipal, List<AgrupamentoClusterResponse> agrupamentosList);
}
