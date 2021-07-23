package br.com.precificacao.service;

import br.com.precificacao.*;
import br.com.precificacao.domain.Agrupamento;
import br.com.precificacao.domain.Contrato;
import br.com.precificacao.domain.Precificacao;
import br.com.precificacao.enums.TaxaProdutoEnum;
import br.com.precificacao.mapper.AgrupamentoMapper;
import br.com.precificacao.mapper.ContratoMapper;
import br.com.precificacao.mapper.PrecificacaoMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class PrecificacaoService extends PrecificacaoServiceGrpc.PrecificacaoServiceImplBase {

    @Autowired
    private ContratoMapper contratoMapper;

    @Autowired
    private AgrupamentoMapper agrupamentoMapper;

    @Autowired
    private PrecificacaoMapper precificacaoMapper;

    @Override
    public void getPrecificacao(PrecificacaoRequest request,
                                StreamObserver<PrecificacaoResponse> responseObserver) {
        List<Agrupamento> agrupamentosPrecificados = new ArrayList<>();
        request.getAgrupamentosList().forEach(agrupamento -> {
            final Agrupamento agrupamentoClusterResponse = precificaAgrupamento(agrupamento);
            agrupamentosPrecificados.add(agrupamentoClusterResponse);
        });
        final String tipoAgrupamentoPrincipal = defineAgrupamentoPrincipal(agrupamentosPrecificados);
        final Precificacao precificacao = precificacaoMapper.toPrecificacao(request, tipoAgrupamentoPrincipal, agrupamentosPrecificados);
        responseObserver.onNext(precificacaoMapper.toPrecificacaoResponse(precificacao));
        responseObserver.onCompleted();
    }

    private String defineAgrupamentoPrincipal(List<Agrupamento> agrupamentosPrecificados) {
        final int maiorQtdContratos = agrupamentosPrecificados.stream()
                .mapToInt(Agrupamento::getContratosCount)
                .max()
                .getAsInt();

        return agrupamentosPrecificados.stream()
                .filter(agrupamentos -> agrupamentos.getContratos().size() == maiorQtdContratos)
                .map(Agrupamento::getTipoAgrupamento)
                .findFirst()
                .orElseGet(() -> "");
    }

    private Agrupamento precificaAgrupamento(AgrupamentoClusterRequest agrupamento) {
        final List<ContratoClusterRequest> contratos = agrupamento.getContratosList();

        final BigDecimal maiorTaxaJuros = getMaiorTaxaJuros(contratos);

        final List<Contrato> contratosPrecificados =
                calculaValoresDosContratos(contratos,
                        maiorTaxaJuros);

        final Double valorTotalSemJuros = contratos.stream()
                .map(ContratoClusterRequest::getValorContrato)
                .reduce(0D, (subtotal, element) -> subtotal + element);

        BigDecimal valorTotalContratoComJuro = contratosPrecificados.stream()
                .map(Contrato::getValorTotalContrato)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2,RoundingMode.HALF_EVEN);

        BigDecimal valorTotalJurosContrato = valorTotalContratoComJuro
                .subtract(BigDecimal.valueOf(valorTotalSemJuros))
                .setScale(2, RoundingMode.HALF_EVEN);


        return agrupamentoMapper.toAgrupamento(agrupamento,valorTotalContratoComJuro,valorTotalJurosContrato, contratosPrecificados);
    }

    private List<Contrato> calculaValoresDosContratos(List<ContratoClusterRequest> contratos,
                                                      BigDecimal maiorTaxaJuros) {
        return contratos.stream()
                .map(contrato -> {
                    final BigDecimal valorContratoComJuro = calculaJurosCompostosContato(maiorTaxaJuros, contrato);
                    final BigDecimal valorTotalJuros = valorContratoComJuro
                            .subtract(BigDecimal.valueOf(contrato.getValorContrato()))
                            .setScale(2, RoundingMode.HALF_UP);
                    return contratoMapper.toContrato(contrato, valorContratoComJuro, valorTotalJuros, maiorTaxaJuros);
                }).collect(Collectors.toList());
    }

    private BigDecimal calculaJurosCompostosContato(BigDecimal maiorTaxaJuros, ContratoClusterRequest contrato) {
        BigDecimal valorContratoComJuro = BigDecimal.valueOf(contrato.getValorContrato());
        final int qtdMesesEmAtraso = (contrato.getQtdeDiasAtraso() / 30 > 0) ? contrato.getQtdeDiasAtraso() / 30 :1 ;

        for (int i = 0; i < qtdMesesEmAtraso; i++) {
            final BigDecimal valorJuros = valorContratoComJuro
                    .divide(BigDecimal.valueOf(100))
                    .multiply(maiorTaxaJuros);
            valorContratoComJuro = valorContratoComJuro.add(valorJuros);
        }
        return valorContratoComJuro.setScale(2,RoundingMode.HALF_EVEN);
    }

    private BigDecimal getMaiorTaxaJuros(List<ContratoClusterRequest> contratos) {
        return contratos.stream()
                .map(contrato -> TaxaProdutoEnum.getTaxaJurosProduto(contrato.getCodigoProduto()).getTaxaJurosMesProduto())
                .distinct()
                .max(BigDecimal::compareTo)
                .orElseGet(() -> BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_EVEN);
    }


}
