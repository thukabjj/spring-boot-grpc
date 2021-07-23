package br.com.orquestrador.simulacao;


import br.com.agrupamento.AgrupamentoRequest;
import br.com.agrupamento.AgrupamentoResponse;
import br.com.agrupamento.AgrupamentoServiceGrpc;
import br.com.orquestrador.controller.request.SimulacaoRequest;
import br.com.orquestrador.controller.response.SimulacaoResponse;
import br.com.orquestrador.domain.agrupamento.Agrupamento;
import br.com.orquestrador.domain.precificacao.Precificacao;
import br.com.orquestrador.mapper.AgrupamentoMapper;
import br.com.orquestrador.mapper.PrecificacaoMapper;
import br.com.orquestrador.mapper.SimulacaoMapper;

import br.com.precificacao.PrecificacaoRequest;
import br.com.precificacao.PrecificacaoResponse;
import br.com.precificacao.PrecificacaoServiceGrpc;
import br.com.simulacao.SimulacaoParceladaRequest;
import br.com.simulacao.SimulacaoParceladaResponse;
import br.com.simulacao.SimulacaoServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulacaoService {

    @GrpcClient("agrupamento-service")
    private AgrupamentoServiceGrpc.AgrupamentoServiceBlockingStub agrupamentoClient;

    @GrpcClient("precificacao-service")
    private PrecificacaoServiceGrpc.PrecificacaoServiceBlockingStub precificacaoClient;

    @GrpcClient("simulacao-parcelada-service")
    private SimulacaoServiceGrpc.SimulacaoServiceBlockingStub simulacaoClient;

    @Autowired
    private AgrupamentoMapper agrupamentoMapper;
    @Autowired
    private PrecificacaoMapper precificacaoMapper;
    @Autowired
    private SimulacaoMapper simulacaoMapper;

    public SimulacaoResponse simulacaoParcelada(SimulacaoRequest request) {
        final AgrupamentoRequest agrupamentoRequest = agrupamentoMapper.toAgrupamentoRequest(request);
        final AgrupamentoResponse agrupamentoResponse = agrupamentoClient.getAgrupamento(agrupamentoRequest);
        Agrupamento agrupamento = agrupamentoMapper.toAgrupamento(agrupamentoResponse);
        final PrecificacaoRequest precificacaoRequest = precificacaoMapper.toPrecificacaoRequest(agrupamento);
        final PrecificacaoResponse precificacaoResponse = precificacaoClient.getPrecificacao(precificacaoRequest);
        final Precificacao precificacao = precificacaoMapper.toPrecificacao(precificacaoResponse);
        final SimulacaoParceladaRequest simulacaoParceladaRequest = simulacaoMapper.toSimulacaoParceladaRequest(precificacao);
        final SimulacaoParceladaResponse simulacaoParceladaResponse = simulacaoClient.getSimulacaoParcelada(simulacaoParceladaRequest);

        return simulacaoMapper.toSimulacaoResponse(simulacaoParceladaResponse);
    }
}
