package br.com.alura.leilao.api.retrofit.client;

import retrofit2.Response;

/**
 * Created by felipebertanha on 13/July/2020
 */
abstract public class WebClient {

    protected <T> boolean temDados(Response<T> response) {
        return response.isSuccessful() && response.body() != null;
    }
}
