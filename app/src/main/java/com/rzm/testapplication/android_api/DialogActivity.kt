package com.rzm.testapplication.android_api

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rzm.testapplication.R

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

    }

    fun showDialog(){
        var dialog = Dialog(this)
        dialog.show()
    }
}