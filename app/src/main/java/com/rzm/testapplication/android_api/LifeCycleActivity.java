package com.rzm.testapplication.android_api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.os.Bundle;
import android.util.Log;

import com.rzm.testapplication.R;

public class LifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
        //监听所有activity生命周期变化，类似getApplication().registerActivityLifecycleCallbacks();
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleEventObserver() {
//            @Override
//            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
//
//            }
//        });


        //TODO 注解方式 ，不推荐 注解方式存在注解处理过程，并且如果在依赖时遗漏注解处理器的话，还会退化为使用反射回调，因此不推荐使用。
        //官方注释：This annotation required the usage of code generation or reflection, which should be avoided. Use DefaultLifecycleObserver or LifecycleEventObserver instead.
//        getLifecycle().addObserver(new MyListener());

        //TODO 非注解方式，推荐
//        getLifecycle().addObserver(new LifecycleEventObserver() {
//            @Override
//            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
//                switch (event) {
//                    case ON_CREATE:
//                        break;
//                    case ON_START:
//                        break;
//                    case ON_RESUME:
//                        break;
//                    case ON_PAUSE:
//                        break;
//                    case ON_STOP:
//                        break;
//                    case ON_DESTROY:
//                        break;
//                    case ON_ANY:
//                        break;
//                }
//            }
//        });

        //TODO 非注解方式，推荐
//        getLifecycle().addObserver(new DefaultLifecycleObserver() {
//            @Override
//            public void onCreate(@NonNull LifecycleOwner owner) {
//                DefaultLifecycleObserver.super.onCreate(owner);
//            }
//
//            @Override
//            public void onStart(@NonNull LifecycleOwner owner) {
//                DefaultLifecycleObserver.super.onStart(owner);
//            }
//
//            @Override
//            public void onResume(@NonNull LifecycleOwner owner) {
//                DefaultLifecycleObserver.super.onResume(owner);
//            }
//
//            @Override
//            public void onPause(@NonNull LifecycleOwner owner) {
//                DefaultLifecycleObserver.super.onPause(owner);
//            }
//
//            @Override
//            public void onStop(@NonNull LifecycleOwner owner) {
//                DefaultLifecycleObserver.super.onStop(owner);
//            }
//
//            @Override
//            public void onDestroy(@NonNull LifecycleOwner owner) {
//                DefaultLifecycleObserver.super.onDestroy(owner);
//            }
//        });
    }

//    public class MyListener implements LifecycleObserver {
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//        public void onCreate() {
//            Log.i("MyLocationListener", "onCreate");
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//        public void onResume() {
//            Log.i("MyLocationListener", "OnResume");
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//        public void OnPause() {
//            Log.i("MyLocationListener", "OnPause");
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//        public void onDestroy() {
//            Log.i("MyLocationListener", "onDestroy");
//        }
//    }
}