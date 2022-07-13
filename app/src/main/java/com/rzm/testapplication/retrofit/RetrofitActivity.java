package com.rzm.testapplication.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .validateEagerly(true)
                .callbackExecutor(new Executor() {
                    @Override
                    public void execute(Runnable command) {

                    }
                })
                .addCallAdapterFactory(new CallAdapter.Factory() {
                    @Nullable
                    @Override
                    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
                        return null;
                    }
                })
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetWorkInterface service = retrofit.create(NetWorkInterface.class);
        //ExecutorCallbackCall == Call
        Call<String> repos = service.listUsers();
        repos.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                okhttp3.Response raw = response.raw();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}