package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by felipebertanha on 13/October/2020
 */
public class LancesLeilaoTelaTest extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivity = new ActivityTestRule<>(ListaLeilaoActivity.class,
            true,
            false);


    @Before
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
        limpaDeDadosInterno();
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberUmLance() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carrp"));

        mainActivity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(allOf(withId(R.id.lances_leilao_fab_adiciona),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.alertTitle),
                withText("Usuários não encontrados"),
                isDisplayed()))
                .check(matches(isDisplayed()));

        onView(allOf(withId(android.R.id.message),
                withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."),
                isDisplayed()))
                .check(matches(isDisplayed()));

        onView(allOf(withId(android.R.id.button1),
                withText("Cadastrar usuário"),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.form_usuario_nome_editText),
                isDisplayed()))
                .perform(click(),
                        typeText("Felipe Bertanha"),
                        closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1),
                withText("Adicionar"),
                isDisplayed()))
                .perform(scrollTo(), click());

        pressBack();

        onView(allOf(withId(R.id.lances_leilao_fab_adiciona),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.alertTitle),
                withText("Novo lance"),
                isDisplayed()))
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.form_lance_valor_edittext),
                isDisplayed()))
                .perform(click(),
                        typeText("200"),
                        closeSoftKeyboard());

        onView(allOf(withId(R.id.form_lance_usuario),
                isDisplayed()))
                .perform(click());

        onData(is(new Usuario(1, "Felipe Bertanha")))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(allOf(withId(android.R.id.button1),
                withText("Propor"),
                isDisplayed()))
                .perform(click());

        //Fazer assertion para as views de maior e menor lance, e tbm, para os maiores lances
        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(200)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(200)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText("200.0 - (1) Felipe Bertanha\n"),
                        isDisplayed())));

    }
}
