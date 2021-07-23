package br.com.precificacao.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Precificacao {
    private Long idCliente;
    private String tipoAgrupamentoPrincipal;
    private List<Agrupamento> agrupamentos;
}
