package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.iasoni.studentapplication.R
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.UserController
import fr.isen.iasoni.studentapplication.Modele.City
import kotlinx.android.synthetic.main.activity_event_info.*
import kotlinx.android.synthetic.main.activity_profil.*


class EventInfoActivity : AppCompatActivity() {

    var idEvent: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)

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
            Toast.makeText(this@EventInfoActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()
            if(activity == "ProfilActivity"){
                startActivity(Intent(this, ProfilActivity::class.java))
            }
            if(activity == "SwipeActivity"){
                val foo = Intent(this, SwipeActivity::class.java)
                foo.putExtra("idEvent", "none")
                this.startActivity(foo)            }
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


        idEvent =  intent.getStringExtra("idEvent")

        val imageView = findViewById<ImageView>(R.id.coverImage)


        var eventController = EventController()
        eventController.getEvent(idEvent){
            name_event.text = it.name
            adresse_event.text =  it.adresse
            description_event.text = it.description
            start_time_event.text = it.start_date
            end_time_event.text = it.end_date
/*
            date_event.text = it.date_added
*/
            num_tickets_event.text = it.limit_user.toString()


            if(it.img != "none" && it.img != null) {
                Glide.with(this).load(it.img).into(imageView)
            }


            //chopper la date
            var city : City = City()
            var cityController = CityController()
            cityController.getCity(it.id_city){
                city_event.text = it.name
            }

            //chopper nom createur event
            var userController = UserController()
            userController.getUser(it.id_user_admin){
                Log.d("nom createur event ->>>",it.name)
                orga_even.text = it.name;
            }
//            var music_array :  ArrayList<String>? = null

            //pour add les chips musique
            var music_array = it.id_music
            Log.d("MUSIC ARRAY ----->>>>>>", music_array.toString())
            val inflater = LayoutInflater.from(this@EventInfoActivity)
            if (music_array != null) {
                for(musicid in music_array) {
                    var musicController = MusicController()
                    musicController.getMusic(musicid){

                        val chips_music = inflater.inflate(R.layout.chips_music,null, false) as Chip

                        chips_music.text = it.name
                        Log.d("MUSIC NAME ---->>>", chips_music.text.toString())



                        chips_group.addView(chips_music)

                    }

                }
            }





        }

        back_arrow.setOnClickListener{
            startActivity(Intent(this, FilterActivity::class.java))

        }

        description_event.setMovementMethod(ScrollingMovementMethod())






    }



}
