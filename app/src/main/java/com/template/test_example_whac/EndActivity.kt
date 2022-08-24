package com.template.test_example_whac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EndActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)


        val ScoreCheck = findViewById<TextView>(R.id.mScoreValue)

        val mSubmit = findViewById<Button>(R.id.button_again)
        val mMenu = findViewById<Button>(R.id.button_Menu)


        val intent = intent
        val ScoreValue = intent.extras!!.getInt("score")


        if (ScoreCheck != null) {
            ScoreCheck.text = ScoreValue.toString()
        }
        mSubmit?.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    GameActivity::class.java
                )
            )
        }

        mMenu?.setOnClickListener {
            val intent = Intent(applicationContext, StartActivity::class.java)
            startActivity(intent)
        }
    }

}