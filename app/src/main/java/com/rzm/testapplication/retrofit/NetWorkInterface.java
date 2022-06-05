package com.rzm.testapplication.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetWorkInterface {
    @GET("users/{user}/repos")
    Call<List<User>> listUsers(@Path("user") String user);
}
