package com.rzm.testapplication.android_api.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

//import com.rzm.testapplication.BR;

// Model
public class User extends BaseObservable {

    private String name;
    private String pwd;
    private String headUrl;

    public User(String name, String pwd, String headUrl) {
        this.name = name;
        this.pwd = pwd;
        this.headUrl = headUrl;
    }

    @Bindable
    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
//        notifyPropertyChanged(BR.headUrl); // APT又是注解处理器技术 BR文件
    }

    @Bindable // BR里面标记生成 name数值标记
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
//        notifyPropertyChanged(BR.name); // APT又是注解处理器技术 BR文件
    }

    @Bindable // BR里面标记生成 pwd数值标记
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
//        notifyPropertyChanged(BR.pwd); // APT又是注解处理器技术 BR文件
    }
}
