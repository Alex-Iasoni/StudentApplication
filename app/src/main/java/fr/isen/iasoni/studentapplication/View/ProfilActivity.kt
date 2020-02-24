package fr.isen.iasoni.studentapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        navigation_view.setOnNavigationItemSelectedListener {item ->

            var message = ""
            when(item.itemId){
                R.id.action_home -> message = "Home"
                R.id.action_notification -> message = "Notification"
                R.id.action_profil -> message = "Profil"
                R.id.action_events -> message = "Events"
                R.id.action_swipe -> message = "Swipe"


            }
            Toast.makeText(this@ProfilActivity, "$message clicked!", Toast.LENGTH_SHORT).show()

            return@setOnNavigationItemSelectedListener true
        }



    }
}
