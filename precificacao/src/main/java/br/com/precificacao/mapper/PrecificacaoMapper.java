package br.com.precificacao.mapper;

import br.com.precificacao.AgrupamentoClusterResponse;
import br.com.precificacao.PrecificacaoRequest;
import br.com.precificacao.PrecificacaoResponse;
import br.com.precificacao.domain.Agrupamento;
import br.com.precificacao.domain.Precificacao;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;
import java.util.Optional;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PrecificacaoMapper {

    @Mapping(target = "agrupamentosList",source = "precificacao.agrupamentos")
    PrecificacaoResponse toPrecificacaoResponse(Precificacao precificacao);

    @Mapping(target = "tipoAgrupamentoPrincipal",source = "tipoAgrupamentoPrincipal")
    Precificacao toPrecificacao(PrecificacaoRequest request,
                                String tipoAgrupamentoPrincipal,
                                List<Agrupamento> agrupamentos);

    @Mapping(target = "contratosList",source = "agrupamentos.contratos")
    AgrupamentoClusterResponse toAgrupamentoClusterResponse(Agrupamento agrupamentos);

    List<AgrupamentoClusterResponse> toAgrupamentoClusterResponse(List<Agrupamento> agrupamentosList);

}
