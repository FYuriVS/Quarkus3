package br.com.yvital.domain;

import br.com.yvital.model.Chave;
import br.com.yvital.model.LinhaDigitavel;
import br.com.yvital.model.StatusPix;
import br.com.yvital.model.Transaction;
import br.com.yvital.repository.TransacaoPixMongoClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class TransactionDomain {

    @Inject
    TransacaoPixMongoClientRepository repository;

    @Transactional
    public void adicionarTransacao(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave) {
        repository.adicionar(linhaDigitavel, valor, chave);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        try {
            return repository.alterarStatusTransacao(uuid, StatusPix.APPROVED);

        } finally {
            iniciarProcessamento(uuid);
        }
    }

    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return repository.alterarStatusTransacao(uuid, StatusPix.REPROVED);
    }

    public Optional<Transaction> iniciarProcessamento(final String uuid) {
        return  repository.alterarStatusTransacao(uuid, StatusPix.IN_PROCESS);
    }

    public Optional<Transaction> findById(final String uuid) {
        Optional<Document> optionalDocument = repository.finOne(uuid);
        return optionalDocument.map(TransactionConverterApply::apply);
    }

}
