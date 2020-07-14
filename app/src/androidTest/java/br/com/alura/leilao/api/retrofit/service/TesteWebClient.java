package br.com.alura.leilao.api.retrofit.service;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.TesteRetrofitInicializador;
import br.com.alura.leilao.api.retrofit.client.WebClient;
import br.com.alura.leilao.model.Leilao;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by felipebertanha on 13/July/2020
 */
public class TesteWebClient extends WebClient {

    private final TesteService service;

    public TesteWebClient() {
        this.service = new TesteRetrofitInicializador().getTesteLeilaoService();
    }

    public Leilao salva(Leilao leilao) throws IOException {
        Call<Leilao> call = service.salva(leilao);
        Response<Leilao> response = call.execute();

        return temDados(response) ? response.body() : null;
    }

    public boolean limpaBancoDeDados() throws IOException {
        Call<Void> call = service.limpaBancoDeDados();

        Response<Void> execute = call.execute();

        return execute.isSuccessful();
    }
}
