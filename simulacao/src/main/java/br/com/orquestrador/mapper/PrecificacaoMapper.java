package br.com.orquestrador.mapper;

import br.com.orquestrador.domain.agrupamento.Agrupamento;
import br.com.orquestrador.domain.precificacao.Precificacao;
import br.com.precificacao.PrecificacaoRequest;
import br.com.precificacao.PrecificacaoResponse;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PrecificacaoMapper {

    @Mapping(target = "allFields",ignore = true)
    PrecificacaoRequest toPrecificacaoRequest(Agrupamento agrupamento);


    Precificacao toPrecificacao(PrecificacaoResponse response);
}
