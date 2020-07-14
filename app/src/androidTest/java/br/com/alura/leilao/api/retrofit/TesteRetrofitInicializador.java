package br.com.alura.leilao.api.retrofit;

import br.com.alura.leilao.api.retrofit.service.TesteService;

/**
 * Created by felipebertanha on 13/July/2020
 */
public class TesteRetrofitInicializador extends RetrofitInicializador {

    public TesteService getTesteLeilaoService() {
        return retrofit.create(TesteService.class);
    }
}
