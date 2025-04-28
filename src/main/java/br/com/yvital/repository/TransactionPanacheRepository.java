package br.com.yvital.repository;

import br.com.yvital.domain.TransactionConverterApply;
import br.com.yvital.model.Chave;
import br.com.yvital.model.LinhaDigitavel;
import br.com.yvital.model.StatusPix;
import br.com.yvital.model.Transaction;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TransactionPanacheRepository implements PanacheMongoRepository<Transaction> {

    public void adicionar(LinhaDigitavel linhaDigitavel, BigDecimal valor, Chave chave) {
        var transaction = new Transaction();
        transaction.setChave(chave.chave());
        transaction.setData(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        transaction.setId(linhaDigitavel.uuid());
        transaction.setStatus(StatusPix.CREATED);
        transaction.setValor(valor);
        transaction.setLinha(linhaDigitavel.linha());
        transaction.setTipoChave(chave.tipoChave().toString());
        transaction.persist();
    }

    public Optional<Transaction> alterarStatusTransacao(String uuid, StatusPix statusPix) {
        Optional<Transaction> optionaltransaction = finOne(uuid);
        if(optionaltransaction.isPresent()) {
            var transaction = optionaltransaction.get();
            transaction.setStatus(statusPix);
            transaction.update();
            return Optional.of(transaction);
        }
        return Optional.empty();
    }

    public Optional<Transaction> finOne(String uuid) {
        return find(TransactionConverterApply.ID, uuid).stream().findFirst();
    }

    public List<Transaction> buscarTransacoes(final Date dataInicio, final Date dataFim) {
       return find("data >= ?1 and data <= ?2 and status = ?3", dataInicio, dataFim, StatusPix.APPROVED ).stream().collect(Collectors.toList());
    }
}
