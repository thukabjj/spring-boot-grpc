package br.com.simulacaoparcelada.service;

import br.com.simulacao.*;
import br.com.simulacaoparcelada.domain.Agrupamento;
import br.com.simulacaoparcelada.domain.SimulacaoParceladaPlano;
import br.com.simulacaoparcelada.enums.DescontoAgrupamentoEnum;
import br.com.simulacaoparcelada.enums.PlanosEnum;
import br.com.simulacaoparcelada.enums.ValorMinimoPlanoEnum;
import br.com.simulacaoparcelada.mapper.SimulacaoParceladoMapper;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class SimulacaoParceladaService extends SimulacaoServiceGrpc.SimulacaoServiceImplBase {

    @Autowired
    private final SimulacaoParceladoMapper simulacaoParceladoMapper;

    @Override
    public void getSimulacaoParcelada(SimulacaoParceladaRequest request,
                                      StreamObserver<SimulacaoParceladaResponse> responseObserver) {
        List<Agrupamento> agrupamentosSimulados = new ArrayList<>();
        request.getAgrupamentosList()
                .forEach(agrupamentoClusterRequest -> {
                    final DescontoAgrupamentoEnum descontoAgrupamento = DescontoAgrupamentoEnum
                            .getDescontoAgrupamento(agrupamentoClusterRequest.getTipoAgrupamento());
                    final BigDecimal percentualDescontoAgrupamento = descontoAgrupamento.getPercentualDesconto();

                    final List<SimulacaoParceladaPlano> planosSimulados = Arrays.stream(PlanosEnum.values())
                            .map(PlanosEnum::getQuantidadeParcelas)
                            .filter(qtdParcelas -> filtraParcelasPorValorMinimo(agrupamentoClusterRequest, percentualDescontoAgrupamento, qtdParcelas))
                            .map(quantidadeParcelas -> calculaPlanoDoAgrupamento(agrupamentoClusterRequest, percentualDescontoAgrupamento, quantidadeParcelas)).collect(Collectors.toList());
                    final BigDecimal valorDesconto = planosSimulados.stream().map(SimulacaoParceladaPlano::getValorDescontoParcela).reduce((x, y) -> x.add(y)).orElseGet(() -> BigDecimal.ZERO);
                    final BigDecimal valorTotalSemDesconto = planosSimulados.stream().map(SimulacaoParceladaPlano::getValorTotalParcela).reduce((x, y) -> x.add(y)).orElseGet(() -> BigDecimal.ZERO);
                    final BigDecimal valorTotalComDesconto = planosSimulados.stream().map(SimulacaoParceladaPlano::getValorTotalParcelaComDesconto).reduce((x, y) -> x.add(y)).orElseGet(() -> BigDecimal.ZERO);
                    final Agrupamento agrupamentoResponse = simulacaoParceladoMapper.toAgrupamento(request, agrupamentoClusterRequest.getContratosList(), valorDesconto, valorTotalSemDesconto, valorTotalComDesconto, planosSimulados);
                    agrupamentosSimulados.add(agrupamentoResponse);
                });
        final SimulacaoParceladaResponse response = simulacaoParceladoMapper.toSimulacaoParceladaResponse(request.getIdCliente(), agrupamentosSimulados);
        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }

    private SimulacaoParceladaPlano calculaPlanoDoAgrupamento(AgrupamentoClusterRequest agrupamentoClusterRequest, BigDecimal percentualDescontoAgrupamento, Long quantidadeParcelas) {
        final BigDecimal valorTotalAgrupamento = BigDecimal.valueOf(agrupamentoClusterRequest.getValorTotal());
        final BigDecimal valorTotalParcelaSemDesconto = calculaValorTotalParcelaSemDesconto(quantidadeParcelas,
                valorTotalAgrupamento);
        final BigDecimal valorTotalDescontoParcela = calculaValorTotalDeDescontoDaParcela(percentualDescontoAgrupamento, valorTotalParcelaSemDesconto);
        final BigDecimal valorTotalParcelaComDesconto = valorTotalParcelaSemDesconto.subtract(valorTotalDescontoParcela);
        return SimulacaoParceladaPlano.builder()
                .quantidadeParcelasPlano(quantidadeParcelas)
                .valorTotalParcela(valorTotalAgrupamento)
                .valorTotalParcelaComDesconto(valorTotalParcelaComDesconto)
                .percentualDescontoParcela(percentualDescontoAgrupamento)
                .valorDescontoParcela(valorTotalDescontoParcela)
                .build();
    }

    private BigDecimal calculaValorTotalDeDescontoDaParcela(BigDecimal percentualDescontoAgrupamento, BigDecimal valorTotalParcelaSemDesconto) {
        return valorTotalParcelaSemDesconto
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN)
                .multiply(percentualDescontoAgrupamento);
    }

    private BigDecimal calculaValorTotalParcelaSemDesconto(Long quantidadeParcelas, BigDecimal valorTotalAgrupamento) {
        return valorTotalAgrupamento
                .divide(new BigDecimal(quantidadeParcelas), 2, RoundingMode.HALF_EVEN);
    }

    private boolean filtraParcelasPorValorMinimo(AgrupamentoClusterRequest agrupamentoClusterRequest, BigDecimal percentualDescontoAgrupamento, Long qtdParcelas) {
        final BigDecimal valorTotalAgrupamento = BigDecimal.valueOf(agrupamentoClusterRequest.getValorTotal());

        final BigDecimal valorTotalParcelaSemDesconto = calculaValorTotalParcelaSemDesconto(qtdParcelas, valorTotalAgrupamento);
        final BigDecimal valorTotalDescontoParcela = calculaValorTotalDeDescontoDaParcela(percentualDescontoAgrupamento, valorTotalParcelaSemDesconto);
        final BigDecimal valorTotalParcelaComDesconto = valorTotalParcelaSemDesconto.subtract(valorTotalDescontoParcela);
        return valorTotalParcelaComDesconto.compareTo(ValorMinimoPlanoEnum.VALOR_MINIMO_PLANO_50_REAIS.getValue()) > 0;
    }

}
