package br.com.simulacaoparcelada.mapper;

import br.com.simulacao.AgrupamentoResponse;
import br.com.simulacao.ContratoClusterRequest;
import br.com.simulacao.SimulacaoParceladaRequest;
import br.com.simulacao.SimulacaoParceladaResponse;
import br.com.simulacaoparcelada.domain.Agrupamento;
import br.com.simulacaoparcelada.domain.SimulacaoParceladaPlano;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface SimulacaoParceladoMapper {

    Agrupamento toAgrupamento(final SimulacaoParceladaRequest request,
                              final List<ContratoClusterRequest> contratos,
                              final BigDecimal valorDesconto,
                              final BigDecimal valorTotalSemDesconto,
                              final BigDecimal valorTotalComDesconto,
                              final List<SimulacaoParceladaPlano> planos);

    @Mappings({
            @Mapping(target = "planosList",source = "agrupamento.planos"),
            @Mapping(target = "contratosList",source = "agrupamento.contratos"),
    })
    AgrupamentoResponse toAgrupamentoResponse(Agrupamento agrupamento);

    @Mapping(target = "allFields",ignore = true)
    SimulacaoParceladaResponse toSimulacaoParceladaResponse(final Long idCliente,
                                                            final List<Agrupamento> agrupamentosList);

}
