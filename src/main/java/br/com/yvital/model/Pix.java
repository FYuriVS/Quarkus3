package br.com.yvital.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public record Pix(
        @Schema(description = "Chave cadastrado do recebedor") String chave,
        @Schema(description = "Valor da transação") BigDecimal valor,
        @Schema(description = "Cidade remetente do recebedor") String cidadeRemetente) {
}
