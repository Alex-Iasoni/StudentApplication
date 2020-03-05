package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.iasoni.studentapplication.R
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.graphics.Paint
import android.util.Log
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.SchoolController
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

//        var cityController  = CityController()
//        cityController.addCity("Toulon")
//        cityController.addCity("La Seyne-Sur-Mer")
//        cityController.addCity("Six-Four")
//        cityController.addCity("Sanary")
//
//        var schoolController = SchoolController()
//        schoolController.addSchool("ISEN Yncréa Méditerranée", "Maison du numérique et de l'innovation, Place Georges Pompidou, 83000 Toulon","")
//        schoolController.addSchool("SeaTech", "Avenue de l'Université, 83130 La Garde","")
//
//        var musicController = MusicController()
//        musicController.addMusic("Rap")

    }


}
