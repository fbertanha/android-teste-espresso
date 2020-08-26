package br.com.alura.leilao.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by felipebertanha on 24/August/2020
 */
public class ViewMatcher {

    public static Matcher<? super View> apareceLeilaoNaPosicao(final int position,
                                                               final String descricaoEsperada,
                                                               final double maiorLanceEsperado) {

        final FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        final String maiorLanceFormatado = formatador.formata(maiorLanceEsperado);

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            private Matcher<View> displayed = isDisplayed();

            @Override
            public void describeTo(Description description) {
                description.appendText("View com descrição")
                        .appendValue(descricaoEsperada)
                        .appendText(", maior lance ")
                        .appendValue(maiorLanceFormatado)
                        .appendText(" na posição ")
                        .appendValue(position)
                        .appendText(" ")
                        .appendDescriptionOf(displayed);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);

                if (viewHolder == null) {
                    throw new IndexOutOfBoundsException("ViewHolder da posicão " + position + " não encontrada");
                }

                View view = viewHolder.itemView;

                boolean temDescricaoEsperada = verificaDescricaoEsperada(view);

                boolean temMaiorLanceEsperado = verificaMaiorLanceEsperado(view);

                return temDescricaoEsperada
                        && temMaiorLanceEsperado
                        && displayed.matches(view);
            }

            private boolean verificaMaiorLanceEsperado(View view) {
                TextView textViewMaiorLance = view.findViewById(R.id.item_leilao_maior_lance);
                return textViewMaiorLance.getText().toString().equals(maiorLanceFormatado);
            }

            private boolean verificaDescricaoEsperada(View view) {
                TextView textViewDescricao = view.findViewById(R.id.item_leilao_descricao);
                return textViewDescricao.getText().toString().equals(descricaoEsperada);
            }
        };
    }
}
