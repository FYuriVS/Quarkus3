package br.com.yvital.service;

import br.com.yvital.config.RedisCache;
import br.com.yvital.model.Chave;
import br.com.yvital.model.TipoChave;
import br.com.yvital.model.TipoPessoa;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@ApplicationScoped
public class DictService {

    @ConfigProperty(name = "pix.chave")
    private String chave;
    @ConfigProperty(name = "pix.ispb")
    private String ispb;
    @ConfigProperty(name = "pix.cnpj")
    private String cnpj;
    @ConfigProperty(name = "pix.nome")
    private String nome;

    @Inject
    RedisCache redisCache;

    public Chave buscarDetalhesChave(String key){
        var chave = buscarChaveCache(key);
        if (Objects.isNull(chave)){
            var chaveFake = buscarChave(key);
            redisCache.set(key, buscarChave(key));
            return chaveFake;
        }

        return chave;
    }

    private Chave buscarChaveCache(String key){
        var chave = redisCache.get(key);
        Log.infof("Chave encontrada no cache %s", chave);
        return chave;
    }

    public Chave buscarChave(String chave) {
        return  new Chave(
                TipoChave.EMAIL,
                chave,
                ispb,
                TipoPessoa.JURIDICA,
                cnpj,
                nome,
                LocalDateTime.now()
        );
    }
}