package com.template.test_example_whac

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class GameActivity : AppCompatActivity() {

    var varRandMole = 0
    private var mTimeView: TextView? = null
    private var mScoreView: TextView? = null
    var varScore = 0
    val handler = Handler()
    var varClose = false


    private val maxTime = 30 * 1000
    private val stepTime = (1 * 1000).toLong()

    var timeInterval = 1000
    var moleUpTime = 350

    var mTimer: CountDownTimer = myTimer(maxTime, stepTime)

    var mPlayerWhack: MediaPlayer? = null
    var mPlayerMiss: MediaPlayer? = null

    var currentDiff: String? = null

    var molesClick = arrayOfNulls<ImageView>(9)

    var yValue = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        mTimeView = findViewById<View>(R.id.textTimeVal) as TextView
        mScoreView = findViewById<View>(R.id.textScoreVal) as TextView

        val sharedPref = getPreferences(MODE_PRIVATE)
        currentDiff = sharedPref.getString("saved_difficulty", "Medium")

        // Start the game!

        // Start the game!
        mTimer.start()
        handler.post(moleLoop)

        varClose = false

        mPlayerWhack = MediaPlayer.create(applicationContext, R.raw.whack)
        mPlayerMiss = MediaPlayer.create(applicationContext, R.raw.miss)
        molesClick[0] = findViewById<ImageView>(R.id.imageMole1)
        molesClick[1] = findViewById<ImageView>(R.id.imageMole2)
        molesClick[2] = findViewById<ImageView>(R.id.imageMole3)
        molesClick[3] = findViewById<ImageView>(R.id.imageMole4)
        molesClick[4] = findViewById<ImageView>(R.id.imageMole5)
        molesClick[5] = findViewById<ImageView>(R.id.imageMole6)
        molesClick[6] = findViewById<ImageView>(R.id.imageMole7)
        molesClick[7] = findViewById<ImageView>(R.id.imageMole8)
        molesClick[8] = findViewById<ImageView>(R.id.imageMole9)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val sHeight = metrics.heightPixels
        yValue = sHeight / 12 * -1

    }


    override fun onPause() {
        super.onPause()
        varClose = true
        mTimer.cancel()
        mPlayerWhack!!.stop()
        mPlayerMiss!!.stop()
    }

    override fun onStop() {
        super.onStop()
        varClose = true
        mTimer.cancel()
        mPlayerWhack!!.stop()
        mPlayerMiss!!.stop()
    }
    override fun onResume() {
        super.onResume()
        varClose = false
    }

  inner class myTimer(maxTime: Int, stepTime: Long) :
        CountDownTimer(maxTime.toLong(), stepTime) {
        override fun onFinish() {
            cancel()
            val messageTime: String ="You ran out of time!"
           EndGame2(varScore, messageTime)

            timeInterval = 1000
            moleUpTime = 20
        }

        override fun onTick(millisUntilFinished: Long) {
            mTimeView?.setText((millisUntilFinished / 1000).toString())
            if (millisUntilFinished / 1000 % 5 == 0L && millisUntilFinished / 1000 != 60L) {
             ///   increaseDifficulty()
            }
        }
    }


    fun EndGame2(score: Int, reason: String?) {
        val intent = Intent(applicationContext, EndActivity::class.java)
        intent.putExtra("score", score)
        intent.putExtra("reason", reason)
        mTimer.cancel()
        startActivity(intent)
        finish()
    }

    var moleLoop: Runnable = object : Runnable {
        var varPrevRandMole = 10
        override fun run() {
            varRandMole = Random().nextInt(8)
            if (varRandMole == varPrevRandMole) {
                do varRandMole = Random().nextInt(8) while (varRandMole == varPrevRandMole)
            }
            varPrevRandMole = varRandMole
            molesClick[varRandMole]!!.animate().translationY(yValue.toFloat()).duration =
                moleUpTime.toLong()
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (!varClose) {
                        for (i in 0..8) {
                            if (molesClick[i]!!.translationY == yValue.toFloat()) {
                                runOnUiThread {
                                    molesClick[i]!!.animate().translationY(0f).duration = 5
                                }
                                if (mPlayerMiss!!.isPlaying && mPlayerMiss != null) {
                                    mPlayerMiss!!.stop()
                                    mPlayerMiss!!.reset()
                                    mPlayerMiss!!.release()
                                }
                                mPlayerMiss!!.start()
                            }
                        }
                    }
                }
            }, timeInterval.toLong())
            if (!varClose) {
                handler.postDelayed(this, timeInterval.toLong())
            }
        }
    }

    fun updateScore(Score: Int) {
        mScoreView!!.text = Score.toString()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.imageMole1 -> if (molesClick[0]!!.translationY < 0) {
                molesClick[0]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole2 -> if (molesClick[1]!!.translationY < 0) {
                molesClick[1]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole3 -> if (molesClick[2]!!.translationY < 0) {
                molesClick[2]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole4 -> if (molesClick[3]!!.translationY < 0) {
                molesClick[3]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole5 -> if (molesClick[4]!!.translationY < 0) {
                molesClick[4]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole6 -> if (molesClick[5]!!.translationY < 0) {
                molesClick[5]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole7 -> if (molesClick[6]!!.translationY < 0) {
                molesClick[6]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole8 -> if (molesClick[7]!!.translationY < 0) {
                molesClick[7]!!.animate().translationY(0f).duration = 20
                directHit()
            }
            R.id.imageMole9 -> if (molesClick[8]!!.translationY < 0) {
                molesClick[8]!!.animate().translationY(0f).duration = 20
                directHit()
            }
        }
    }

    fun directHit() {
        if (mPlayerWhack != null && mPlayerWhack!!.isPlaying) {
            mPlayerWhack!!.stop()
            mPlayerWhack!!.reset()
            mPlayerWhack!!.release()
        }

        varScore += 250
        updateScore(varScore)
    }
}