package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.iasoni.studentapplication.Adapters.EventAdapter
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_event.*

class EventsActivity : AppCompatActivity() {

    var event_filter: String? = ""
    lateinit var optionFilter : Spinner



    var music: String? = ""
    var arrayMusic = ArrayList<String>()
    var school: String? = ""
    var ville: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)


        navigation_view_event.setSelectedItemId(R.id.action_home);


        navigation_view_event.setOnNavigationItemSelectedListener {item ->

            var activity = ""

            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "passedEventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"


            }
            Toast.makeText(this@EventsActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()

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

        add_red.setOnClickListener {
            val foo = Intent(this, CreateEventActivity::class.java)
            this.startActivity(foo)
        }
        back_arrow.setOnClickListener {
            val foo = Intent(this, FilterActivity::class.java)
            this.startActivity(foo)
        }


        var eventList = ArrayList<Event>()
        var interrested_list = ArrayList<Boolean>()

        event_filter =  intent.getStringExtra("event_filter") //filtre de l'event


        if(event_filter == "city"){
            filter_name.text = "Par ville"

            optionFilter = findViewById(R.id.spinnerFilter) as Spinner

            var cityController  = CityController()
            var optionsVilles =  ArrayList<String?>()

            cityController.getCities{

                for(city in it){
                    optionsVilles.add(city.name)
                }
                optionFilter.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsVilles)

                optionFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        ville = optionsVilles.get(position)
                    }
                }
            }




        }else if (event_filter == "school"){
            filter_name.text = "Par école"


        }else if(event_filter == "music"){
            filter_name.text = "Par style musical"




        }else if(event_filter == "student"){
            filter_name.text = "Par étudiants"

        }



        eventRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        eventRecyclerView.adapter = EventAdapter(interrested_list,eventList, this)
    }
}
