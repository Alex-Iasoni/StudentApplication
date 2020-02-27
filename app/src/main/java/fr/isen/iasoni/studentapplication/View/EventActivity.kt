package fr.isen.iasoni.studentapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.iasoni.studentapplication.Adapters.BadgeAdapter
import fr.isen.iasoni.studentapplication.Adapters.EventAdapter
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_profil.*

class EventActivity : AppCompatActivity() {

    var event_filter: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        var eventList = ArrayList<Event>()
        var interrested_list = ArrayList<Boolean>()

        event_filter =  intent.getStringExtra("event_filter") //filtre de l'event


        if(event_filter == "city"){
            filter_name.text = "Par ville"
            var event_city = Event();
            event_city.name = "Derhka à Toulon"
            event_city.description = "Ca va pas etre des lol"
            eventList.add(event_city)
            interrested_list.add(false)

            var event_city_2 = Event();
            event_city_2.name = "Chuppitos nuque instante"
            event_city_2.description = "Surement Killian Collet présent ça va etre tb"
            eventList.add(event_city_2)
            interrested_list.add(true)

        }else if (event_filter == "school"){
            filter_name.text = "Par école"
            var event_school = Event();
            event_school.name = "ISEN + TOULON C TB"
            event_school.description = "Que des folass c sur"
            eventList.add(event_school)
            interrested_list.add(true)

            var event_school_2 = Event();
            event_school_2.name = "Soirée annulé 0 drogues"
            event_school_2.description = "Tu peux vrmt prendre ta demi-patate"
            eventList.add(event_school_2)
            interrested_list.add(true)

        }else if(event_filter == "music"){
            filter_name.text = "Par style musical"
            var event_music = Event();
            event_music.name = "Que de la Trans c choquant"
            event_music.description = "Rien pour les chiens"
            eventList.add(event_music)
            interrested_list.add(false)



        }else if(event_filter == "student"){
            filter_name.text = "Par étudiants"
            var event_student = Event();
            event_student.name = "Clape-Isen only"
            event_student.description = "Gogolneim est chaud 1v1 Coronavirus"
            eventList.add(event_student)
            interrested_list.add(false)
        }



        eventRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        eventRecyclerView.adapter = EventAdapter(interrested_list,eventList, this)
    }
}
