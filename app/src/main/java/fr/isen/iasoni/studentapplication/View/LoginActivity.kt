package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.iasoni.studentapplication.R
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.graphics.Paint
import android.util.Log
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Modele.Music
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(fr.isen.iasoni.studentapplication.R.layout.activity_login)


        register.setPaintFlags(register.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }


}
