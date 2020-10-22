package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.database.dao.UsuarioDAO;
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

        propoeNovoLance("200", 1, "Felipe Bertanha");

        //Fazer assertion para as views de maior e menor lance, e tbm, para os maiores lances
        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(200)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(200)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatador.formata(200) + " - (1) Felipe Bertanha\n"),
                        isDisplayed())));

    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberTresLances() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        tentaSalvarUsuariosNoBancoDeDadosLocal(new Usuario("Felipe"), new Usuario("Evelyn"));

        mainActivity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        propoeNovoLance("200", 1, "Felipe");

        propoeNovoLance("300", 2, "Evelyn");

        propoeNovoLance("400", 1, "Felipe");

        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(400)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(200)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatador.formata(400) + " - (1) Felipe\n" +
                                formatador.formata(300) + " - (2) Evelyn\n" +
                                formatador.formata(200) + " - (1) Felipe\n"),
                        isDisplayed())));

    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberUmLanceMuitoAlto() throws IOException {
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        tentaSalvarUsuariosNoBancoDeDadosLocal(new Usuario("Felipe"));

        mainActivity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        propoeNovoLance("2000000000", 1, "Felipe");

        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatador.formata(2000000000)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatador.formata(2000000000)),
                        isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatador.formata(2000000000) + " - (1) Felipe\n"),
                        isDisplayed())));
    }

    private void propoeNovoLance(String valor, int idUsuario, String nomeUsuario) {
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
                        typeText(valor),
                        closeSoftKeyboard());

        onView(allOf(withId(R.id.form_lance_usuario),
                isDisplayed()))
                .perform(click());

        onData(is(new Usuario(idUsuario, nomeUsuario)))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(allOf(withId(android.R.id.button1),
                withText("Propor"),
                isDisplayed()))
                .perform(click());
    }

    private void tentaSalvarUsuariosNoBancoDeDadosLocal(@NonNull Usuario... usuarios) {
        UsuarioDAO dao = new UsuarioDAO(InstrumentationRegistry.getTargetContext());

        for (Usuario usuario : usuarios) {
            Usuario usuarioSalvo = dao.salva(usuario);

            if (usuarioSalvo == null) {
                Assert.fail("Usuario não salvo");
            }
        }
    }
}
