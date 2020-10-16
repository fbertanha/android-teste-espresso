package br.com.alura.leilao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.service.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

/**
 * Created by felipebertanha on 15/October/2020
 */
public abstract class BaseTesteIntegracao {
    private static final String ERRO_FALHA_LIMPEZA_DE_BANCO_DA_API = "Banco de Dados não foi limpo";
    private static final String ERRO_LEILAO_NAO_FOI_SALVO = "Leilão não foi salvo: ";

    private final TesteWebClient webClient = new TesteWebClient();


    protected void limpaBancoDeDadosDaApi() throws IOException {
        if (!webClient.limpaBancoDeDados()) {
            Assert.fail(ERRO_FALHA_LIMPEZA_DE_BANCO_DA_API);
        }
    }

    protected void tentaSalvarLeilaoNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = webClient.salva(leilao);
            if (leilaoSalvo == null) {
                Assert.fail(ERRO_LEILAO_NAO_FOI_SALVO + leilao.getDescricao());
            }
        }

    }

    protected void limpaDeDadosInterno() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(BuildConfig.BANCO_DE_DADOS);
    }
}
