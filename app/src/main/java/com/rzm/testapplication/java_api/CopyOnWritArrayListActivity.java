package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWritArrayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_on_writ_array_list);
    }

    public void testVector(){
        Vector<String> v = new Vector<>();
        for (int i = 0; i < 100; i++) {
            v.add("num"+i);
        }
        Iterator<String> iterator = v.iterator();
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //复合操作下是不安全的，所以要加锁
                    synchronized (iterator) {
                        while (iterator.hasNext()) {
                            String next = iterator.next();
                            if ("num2".equals(next)) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public void start(View view) {
        Vector<String> vector = new Vector<>();
        vector.add("");
        vector.get(0);


        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item"+i);
        }

        String s = list.get(12);
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //根据index移除
                    for (int i1 = 0; i1 < list.size(); i1++) {
                        String s = list.get(i1);
                        System.out.println("bbbbbbbbbb=="+s);
                        if ("item2".equals(s)){
                            System.out.println("移除item2");
                            list.remove(i1);
                        }
                    }
                }
            }).start();
        }
        System.out.println("11111");
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //直接移除对象
                    for (String s : list) {
                        System.out.println("aaaaaaaaaaaa=="+s);
                        if ("item3".equals(s)){
                            System.out.println("移除item3");
                            list.remove(s);
                        }
                    }
                }
            }).start();
        }
        System.out.println("222222");
    }
}