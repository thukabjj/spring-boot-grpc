package br.com.orquestrador.mapper;

import br.com.agrupamento.AgrupamentoRequest;
import br.com.agrupamento.AgrupamentoResponse;
import br.com.orquestrador.controller.request.SimulacaoRequest;
import br.com.orquestrador.domain.agrupamento.Agrupamento;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AgrupamentoMapper {

    @Mapping(target = "contratosList",source = "request.contratos")
    AgrupamentoRequest toAgrupamentoRequest(SimulacaoRequest request);

    Agrupamento toAgrupamento(AgrupamentoResponse response);
}
