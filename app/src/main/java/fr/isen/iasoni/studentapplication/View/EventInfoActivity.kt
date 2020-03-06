package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.iasoni.studentapplication.R
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.chip.Chip
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.UserController
import fr.isen.iasoni.studentapplication.Modele.City
import kotlinx.android.synthetic.main.activity_event_info.*


class EventInfoActivity : AppCompatActivity() {


    var music_array :  ArrayList<String>? = null


    var idEvent: String? = ""





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)


        idEvent =  intent.getStringExtra("idEvent")
        var eventController = EventController()
        eventController.getEvent(idEvent){

            adresse_event.text =  it.adresse
            description_event.text = it.description
            start_time_event.text = it.start_date
            end_time_event.text = it.end_date
            date_event.text = it.date_added
            tickets_event.text = it.limit_user.toString()


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


            //pour add les chips musique
            music_array = it.id_music
            val inflater = LayoutInflater.from(this@EventInfoActivity)
            for(musicid in this!!.music_array!!)
            {
                var musicController = MusicController()
                musicController.getMusic(musicid){

                    val chips_music = inflater.inflate(R.layout.chips_music,null, false) as Chip

                    Log.d("liste musique ->>>",it.name)
                    chips_music.text = it.name
                    chips_music.setOnCloseIconClickListener{
                        chips_group.removeView(it)
                    }
                    chips_group.addView(chips_music)

                }

            }





        }

        backHome.setOnClickListener{
            startActivity(Intent(this, FilterActivity::class.java))

        }

        description_event.setMovementMethod(ScrollingMovementMethod())






    }



}
