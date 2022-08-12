package com.rzm.testapplication.net.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetWorkInterface {

    @GET
    Call<String> listUsers();
}
