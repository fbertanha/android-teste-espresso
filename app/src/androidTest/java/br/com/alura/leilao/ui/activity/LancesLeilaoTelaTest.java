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
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        //salvar leilao na api
        tentaSalvarLeilaoNaApi(new Leilao("Carrp"));

        //Inicializar a Main Activity
        mainActivity.launchActivity(new Intent());

        //Clica no leilao
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Clica no Fab da tela de lances do Leilao
        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        //Verificar titulo e msg do modal de usuario nao cadastrado
        onView(withText("Usuários não encontrados"))
                .check(matches(isDisplayed()));

        onView(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."))
                .check(matches(isDisplayed()));

        //Clica no botao "Cadastrar Usuário"
        onView(withText("Cadastrar usuário"))
                .perform(click());

        //Clica no Fab tela de lista de usuarios

        //Clica no EditText e preenche com o nome do usuario

        //Clica em "Adicionar"

        //Clica no back do Android

        //Clica no fab lances do leilao

        //Verifica visibilidade do dialog com o titulo esperado "Novo lance"

        //Clica no EditText de valor e preenche

        //Seleciona usuário

        //Clica no botao propor

        //Fazer assertion para as views de maior e menor lance, e tbm, para os maiores lances
        

    }
}
