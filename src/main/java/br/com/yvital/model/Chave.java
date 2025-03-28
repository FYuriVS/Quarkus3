package br.com.yvital.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

public record Chave(
        @Schema(description = "Definir qual o tipo de chave pix ex.: cpf, aleatória, email, etc...") TipoChave tipoChave,
        @Schema(description = "Chave Pix") String chave,
        @Schema(description = "Primeiros digitos da istituição financeira") String ispb,
        @Schema(description = "Definir se é pessoa física ou juridica") TipoPessoa tipoPessoa,
        @Schema(description = "Numero do cpf ou cnpj") String cpfCnpj,
        @Schema(description = "Nome do beneficiário") String nome,
        @Schema(description = "Data da transação") LocalDateTime dataHoraCriacao
) {
}
