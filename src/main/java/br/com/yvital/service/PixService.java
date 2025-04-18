package br.com.yvital.service;


import br.com.yvital.config.LinhaDigitavelCache;
import br.com.yvital.domain.TransactionDomain;
import br.com.yvital.model.Chave;
import br.com.yvital.model.LinhaDigitavel;
import br.com.yvital.model.Transaction;
import br.com.yvital.model.qrcode.DadosEnvio;
import br.com.yvital.model.qrcode.QrCode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.imageio.IIOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PixService {

    public static final String QRCODE_PATH ="/tmp/qrcode";

    @Inject
    TransactionDomain transactionDomain;

    @Inject
    LinhaDigitavelCache linhaDigitavelCache;

    public BufferedInputStream gerarQrCode(final String uuid) throws IOException {

        var imagePath = QRCODE_PATH + uuid + ".png";

        try {
            return new BufferedInputStream(new FileInputStream(imagePath));
        } finally {
            Files.delete(Paths.get(imagePath));
        }

    }

    public LinhaDigitavel gerarLinhaDigitavel(final Chave chave, BigDecimal valor, String cidadeRemetente) {

        var qrCode = new QrCode(new DadosEnvio(chave, valor, cidadeRemetente));
        var uuid = UUID.randomUUID().toString();
        var imagePath = QRCODE_PATH + uuid + ".png";
        qrCode.save(Path.of(imagePath));

        String qrCodeString = qrCode.toString();
        var linhaDigitavel =  new LinhaDigitavel(qrCodeString, uuid);
        salvarLinhaDigitavel(chave, valor, linhaDigitavel);
        return linhaDigitavel;
    }

    private void salvarLinhaDigitavel(Chave chave, BigDecimal valor, LinhaDigitavel linhaDigitavel) {
        transactionDomain.adicionarTransacao(linhaDigitavel, valor, chave);
        linhaDigitavelCache.set(linhaDigitavel.uuid(), linhaDigitavel);
    }

    public Optional<Transaction> findById(final String uuid) {
        return transactionDomain.findById(uuid);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        return transactionDomain.aprovarTransacao(uuid);
    }

    public void processarPix(){}

    public Optional<Transaction> reprovarTransacao(final String uuid){
        return transactionDomain.reprovarTransacao(uuid);
    }
}
