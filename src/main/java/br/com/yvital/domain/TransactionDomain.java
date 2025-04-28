package br.com.yvital.domain;

import br.com.yvital.model.Chave;
import br.com.yvital.model.LinhaDigitavel;
import br.com.yvital.model.StatusPix;
import br.com.yvital.model.Transaction;
import br.com.yvital.repository.TransacaoPixMongoClientRepository;
import br.com.yvital.repository.TransactionPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TransactionDomain {

    @Inject
    TransactionPanacheRepository repository;

    public void adicionarTransacao(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave) {
        repository.adicionar(linhaDigitavel, valor, chave);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        try {
            return repository.alterarStatusTransacao(uuid, StatusPix.APPROVED);

        } catch (Exception e) {
            return iniciarProcessamento(uuid);
        }
    }

    public List<Transaction> buscarTransacoes(final Date dataInicio, final Date dataFim) {
        return repository.buscarTransacoes(dataInicio, dataFim);
    }


    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return repository.alterarStatusTransacao(uuid, StatusPix.REPROVED);
    }

    public Optional<Transaction> iniciarProcessamento(final String uuid) {
        return  repository.alterarStatusTransacao(uuid, StatusPix.IN_PROCESS);
    }

    public Optional<Transaction> findById(final String uuid) {
        return repository.finOne(uuid);
    }

}
