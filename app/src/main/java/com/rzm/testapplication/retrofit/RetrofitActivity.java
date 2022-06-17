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
                .addConverterFactory(new Converter.Factory() {
                    @Nullable
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return super.responseBodyConverter(type, annotations, retrofit);
                    }

                    @Nullable
                    @Override
                    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
                        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
                    }

                    @Nullable
                    @Override
                    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return super.stringConverter(type, annotations, retrofit);
                    }
                })
                .build();

        NetWorkInterface service = retrofit.create(NetWorkInterface.class);
//        Call<String> repos = service.listUsers();
//        repos.enqueue(null);
    }
}