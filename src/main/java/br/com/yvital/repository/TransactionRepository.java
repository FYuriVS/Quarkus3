package br.com.yvital.repository;

import br.com.yvital.model.Chave;
import br.com.yvital.model.LinhaDigitavel;
import br.com.yvital.model.StatusPix;
import br.com.yvital.model.Transaction;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository {

    void adicionar(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave);

    Optional<Transaction> alterarStatusTransacao(final String uuid, final StatusPix statusPix);

    Optional<Document> finOne(final String uuid);

}
