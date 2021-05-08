package com.estudo.demo.dto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LancamentoDTO {

    private Long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    private String status;
    private Long usuario;
    private BigDecimal valor;
    private String tipo;

}
