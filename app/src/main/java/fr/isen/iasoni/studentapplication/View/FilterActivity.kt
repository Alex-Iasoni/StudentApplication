package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.activity_profil.navigation_view

class FilterActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)


        //---------------Navigation Menu-------------------------------------------
        navigation_view.setSelectedItemId(R.id.action_home);
        navigation_view.setOnNavigationItemSelectedListener {item ->
            var activity = ""
            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "passedEventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"
            }
            Toast.makeText(this@FilterActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()
            if(activity == "ProfilActivity"){
                startActivity(Intent(this, ProfilActivity::class.java))
            }
            if(activity == "SwipeActivity"){
                startActivity(Intent(this, SwipeActivity::class.java))
            }
            if(activity == "Home"){
                startActivity(Intent(this, FilterActivity::class.java))
            }
            if(activity == "Notification"){
                startActivity(Intent(this, NotifActivity::class.java))
            }
            if(activity == "passedEventsActivity"){
                startActivity(Intent(this, PassedEventsActivity::class.java))
            }
            return@setOnNavigationItemSelectedListener true
        }
        //---------------------------------------------------------------------------------

        shadow_city.setOnClickListener {
            val foo = Intent(this, EventsActivity::class.java)
            foo.putExtra("event_filter", "city")
            this.startActivity(foo)
        }
        shadow_student.setOnClickListener {
            val foo = Intent(this, EventsActivity::class.java)
            foo.putExtra("event_filter", "student")
            this.startActivity(foo)
        }
        shadow_music.setOnClickListener {
            val foo = Intent(this, EventsActivity::class.java)
            foo.putExtra("event_filter", "music")
            this.startActivity(foo)
        }
        shadow_school.setOnClickListener {
            val foo = Intent(this, EventsActivity::class.java)
            foo.putExtra("event_filter", "school")
            this.startActivity(foo)
        }
    }
}
