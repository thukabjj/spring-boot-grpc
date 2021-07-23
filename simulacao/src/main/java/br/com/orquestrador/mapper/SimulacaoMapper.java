package br.com.orquestrador.mapper;

import br.com.orquestrador.controller.response.SimulacaoResponse;
import br.com.orquestrador.domain.precificacao.Precificacao;
import br.com.simulacao.SimulacaoParceladaRequest;
import br.com.simulacao.SimulacaoParceladaResponse;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface SimulacaoMapper {

    @Mapping(target = "allFields",ignore = true)
    SimulacaoParceladaRequest toSimulacaoParceladaRequest(Precificacao precificacao);

    SimulacaoResponse toSimulacaoResponse(SimulacaoParceladaResponse response);
}
