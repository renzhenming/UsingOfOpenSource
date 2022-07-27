package com.rzm.testapplication.android_api.exeception

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class KotlinException {


    fun copyFile(){
        //没有异常提醒
        var inputStream = FileInputStream(File(""))
        var outputStream = FileOutputStream(File(""))
    }
}