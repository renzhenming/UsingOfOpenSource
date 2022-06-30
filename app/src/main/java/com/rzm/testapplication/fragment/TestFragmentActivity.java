package com.rzm.testapplication.fragment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.rzm.testapplication.R;

public class TestFragmentActivity extends AppCompatActivity {

    public static final String TAG = "TestFragmentActivity ";
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        TestFragment testFragment = new TestFragment();
        TestFragment testFragment2 = new TestFragment();
        TestFragment testFragment3 = new TestFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fragmentContainer, testFragment);
        transaction.replace(R.id.fragmentContainer, testFragment2);

        fm.findFragmentById(R.id.fragmentContainer);
        fm.findFragmentByTag("");
        transaction.addToBackStack("");
        fm.popBackStack();
        fm.popBackStackImmediate();
        fm.getFragments();

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        System.out.println(TAG + "onActivityResult, result = " + result != null ? result.getData().toString() : null);
                    }
                });

        fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                System.out.println(TAG + "onFragmentCreated f=" + f);
            }
        }, true);

        //同一Activity下fragment之间的通信（接收消息）
        fm.setFragmentResultListener("resultKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                System.out.println(TAG + "onFragmentResult requestKey=" + requestKey + " result = " + result);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("aaa", "bbb");
        //同一Activity下fragment之间的通信（发送消息）
        fm.setFragmentResult("resultKey", bundle);

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                System.out.println(TAG + "onBackStackChanged");
            }
        });

        transaction.commitAllowingStateLoss();
    }
}