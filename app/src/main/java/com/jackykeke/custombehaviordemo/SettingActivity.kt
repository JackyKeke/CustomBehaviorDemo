package com.jackykeke.custombehaviordemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jackykeke.base.utils.ALog
import kotlinx.android.synthetic.main.activity_setting.*
import java.lang.Boolean


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        openLog.clickWithTrigger {
            if (Boolean.parseBoolean(System.getProperty(ALog.TAG))){
                return@clickWithTrigger
            }
            if (cotinueTimes > 6) {
                System.setProperty(ALog.TAG, "true")
                Toast.makeText(this, "已打开Log", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "还有${7 - cotinueTimes}次打开Log", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


fun <T : View> T.clickWithTrigger(time: Long = 500, block: (T) -> Unit) {

    triggerDelay = time

    setOnClickListener {
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - triggerLastTime <= triggerDelay) {
            cotinueTimes += 1
            block(it as T)
        } else {
            cotinueTimes = 0
        }
        triggerLastTime = currentClickTime
    }

}

var cotinueTimes = 0
var triggerLastTime: Long = 0
var triggerDelay: Long = 0
