package br.com.yvital.model;

public enum StatusPix {

    CREATED, //Transação criada, não finalizada.

    IN_PROCESS, //Transação em processo, não finalizada.

    APPROVED, //Transação aprovada, não finalzada.

    REPROVED, //Transação reprovada, não finalizada.

    DONE, //Transação concluída com sucesso.

    UNDONE, //A transação não pôde ser concluída. O valor foi estornado

    CANCELED // A transação foi cancelada. O saldo não foi afetado.

}
