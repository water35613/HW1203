package com.example.hw_lab9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var progressRabbit = 0;
    private var progressTurtle = 0;
    private lateinit var btn_start : Button
    private lateinit var sb_rabbit : SeekBar
    private lateinit var sb_turtle : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start = findViewById(R.id.btn_start)
        sb_rabbit = findViewById(R.id.sb_rabbit)
        sb_turtle = findViewById(R.id.sb_turtle)

        btn_start.setOnClickListener {
            btn_start.isEnabled = false
            progressRabbit = 0
            progressTurtle = 0
            sb_rabbit.progress =0
            sb_turtle.progress =0
            runRabbit()
            runTurtle()
        }
    }
    private fun showToast (msg: String) =
        Toast.makeText(this , msg  , Toast.LENGTH_SHORT).show()

    private val handler = Handler(Looper.getMainLooper()){  msg ->
        if (msg.what == 1)
            sb_rabbit.progress = progressRabbit
        if (progressRabbit >= 100 && progressTurtle < 100){
            showToast("兔子勝利")
            btn_start.isEnabled = true
        }
        true
    }

    private fun runRabbit() {
        Thread{
            val sleepProbability = arrayOf(true , true ,false)
            while (progressRabbit < 100 && progressTurtle < 100){
                try {
                    Thread.sleep(100)
                    if (sleepProbability.random())
                        Thread.sleep(300)
                } catch (e: InterruptedException){
                    e.printStackTrace()
                }
                progressRabbit += 3

                val msg = Message()
                msg.what =1
                handler.sendMessage(msg)
            }
        }.start()
    }
    private fun runTurtle () {
        GlobalScope.launch (Dispatchers.Main){
            while (progressTurtle < 100 && progressRabbit < 100){
                delay(100)
                progressTurtle += 1
                sb_turtle.progress = progressTurtle

                if (progressTurtle >= 100 && progressRabbit < 100)
                {
                    showToast("烏龜勝利")
                    btn_start.isEnabled =true
                }

            }
        }
    }
}