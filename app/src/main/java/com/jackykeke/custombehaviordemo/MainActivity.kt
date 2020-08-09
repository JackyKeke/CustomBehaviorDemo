package com.jackykeke.custombehaviordemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setting(view: View) {
        startActivity(Intent(this,SettingActivity::class.java))
    }

    fun customizeBehavior(view: View) {
        startActivity(Intent(this,BehaviorActivity::class.java))
    }
}