package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.service.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by felipebertanha on 06/July/2020
 */
public class ListaLeilaoTelaTest {

    private static final String ERRO_FALHA_LIMPEZA_DE_BANCO_DA_API = "Banco de Dados não foi limpo";
    private static final String ERRO_LEILAO_NAO_FOI_SALVO = "Leilão não foi salvo: ";
    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    private final TesteWebClient webClient = new TesteWebClient();

    @Before
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    @Test
    public void deve_AparecerUmLeilao_QuandoCarregarUmLeilaoNaApi() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        activity.launchActivity(new Intent());

        onView(withText("Carro"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        tentaSalvarLeilaoNaApi(
                new Leilao("Carro"),
                new Leilao("Computador"));

        activity.launchActivity(new Intent());

        onView(withText("Carro"))
                .check(matches(isDisplayed()));

        onView(withText("Computador"))
                .check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    private void tentaSalvarLeilaoNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = webClient.salva(leilao);
            if (leilaoSalvo == null) {
                Assert.fail(ERRO_LEILAO_NAO_FOI_SALVO + leilao.getDescricao());
            }
        }

    }

    private void limpaBancoDeDadosDaApi() throws IOException {
        if (!webClient.limpaBancoDeDados()) {
            Assert.fail(ERRO_FALHA_LIMPEZA_DE_BANCO_DA_API);
        }
    }
}