package br.com.agrupamento.service;

import br.com.agrupamento.*;
import br.com.agrupamento.enums.ProdutoAgrupamentoEnum;
import br.com.agrupamento.mapper.AgrupamentoMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;


@GrpcService
public class AgrupamentoService extends AgrupamentoServiceGrpc.AgrupamentoServiceImplBase {

    @Autowired
    private AgrupamentoMapper agrupamentoMapper;

    @Override
    public void getAgrupamento(AgrupamentoRequest request,
                               StreamObserver<AgrupamentoResponse> responseObserver) {

        List<AgrupamentoClusterResponse> clusterResponses = getAgrupamentoClusterResponses(request);
        final String tipoAgrupamentoSugerido = getTipoAgrupamentoSugerido(clusterResponses);
        final AgrupamentoResponse agrupamentoResponse = agrupamentoMapper.toAgrupamentoResponse(request.getIdCliente(), tipoAgrupamentoSugerido, clusterResponses);
        responseObserver.onNext(agrupamentoResponse);
        responseObserver.onCompleted();
    }

    private String getTipoAgrupamentoSugerido(List<AgrupamentoClusterResponse> clusterResponses) {
        String tipoAgrupamentoSugerido = "";
        final OptionalInt max = clusterResponses.stream()
                .map(AgrupamentoClusterResponse::getContratosList)
                .mapToInt(List::size)
                .max();
       tipoAgrupamentoSugerido = clusterResponses.stream()
                .filter(c -> max.getAsInt() == c.getContratosList().size())
                .map(AgrupamentoClusterResponse::getTipoAgrupamento)
                .findFirst().orElse( "");
        return tipoAgrupamentoSugerido;
    }

    private List<AgrupamentoClusterResponse> getAgrupamentoClusterResponses(AgrupamentoRequest request) {
        Map<ProdutoAgrupamentoEnum, List<ContratoClusterResponse>> agrupamentoContratosRequest = new HashMap<>();

        Arrays.asList(ProdutoAgrupamentoEnum.values()).forEach(categoria ->
        {
            final List<ContratoRequest> contratosCategoria = request.getContratosList().stream().filter(
                    contratoRequest -> {
                final ProdutoAgrupamentoEnum tipoAgrupamentoProduto =
                        ProdutoAgrupamentoEnum.getTipoAgrupamentoProduto(contratoRequest.getCodigoProduto());
                return categoria.equals(tipoAgrupamentoProduto);
            }).collect(Collectors.toList());
            agrupamentoContratosRequest.put(categoria, agrupamentoMapper.toContratoClusterResponseList(contratosCategoria));
        });

        List<AgrupamentoClusterResponse> clusterResponses = new ArrayList<>();
        agrupamentoContratosRequest.forEach((key,value) -> {
            final AgrupamentoClusterResponse agrupamentoClusterResponse = agrupamentoMapper.toAgrupamentoClusterResponse(key.getTipoAgrupamento(), value);
            clusterResponses.add(agrupamentoClusterResponse);
        });
        return clusterResponses;
    }
}
