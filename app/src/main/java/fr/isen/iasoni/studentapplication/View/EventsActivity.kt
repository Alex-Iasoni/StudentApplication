package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Adapters.EventAdapter
import fr.isen.iasoni.studentapplication.Controller.*
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_create_event.*
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_event.buttonAdd
import kotlinx.android.synthetic.main.activity_event.chipGroupMusic

class EventsActivity : AppCompatActivity() {

    var event_filter: String? = ""
    lateinit var optionFilter : Spinner



    var music: String? = ""
    var arrayMusic = ArrayList<String?>()
    var school: String? = ""
    var ville: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        navigation_view_event.setSelectedItemId(R.id.action_home);


        navigation_view_event.setOnNavigationItemSelectedListener { item ->

            var activity = ""

            when (item.itemId) {
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "passedEventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"


            }
            Toast.makeText(this@EventsActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()

            if (activity == "ProfilActivity") {

                startActivity(Intent(this, ProfilActivity::class.java))
            }
            if (activity == "SwipeActivity") {
                startActivity(Intent(this, SwipeActivity::class.java))
            }
            if (activity == "Home") {
                startActivity(Intent(this, FilterActivity::class.java))
            }
            if (activity == "Notification") {
                startActivity(Intent(this, NotifActivity::class.java))
            }
            if (activity == "passedEventsActivity") {
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

        event_filter = intent.getStringExtra("event_filter") //filtre de l'event


        if (event_filter == "city") {

            var play = findViewById(R.id.buttonAdd) as Button
            play.isClickable=false
            play.visibility= View.INVISIBLE // v letter should be capital


            filter_name.text = "Par ville"

            optionFilter = findViewById(R.id.spinnerFilter) as Spinner

            var cityController = CityController()
            var optionsVilles = ArrayList<String?>()

            cityController.getCities {

                for (city in it) {
                    optionsVilles.add(city.name)
                }
                optionFilter.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsVilles)

                optionFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

            searchButton.setOnClickListener {
                val uid = FirebaseAuth.getInstance().uid ?: ""
                var eventController: EventController = EventController()

                eventController.FilterEventInterestUser(ville, null, arrayListOf(), uid) {
                    var interestArray : ArrayList<Boolean?> = ArrayList<Boolean?>()
                    interestArray = it

                    eventController.FilterCityEvent(ville) {

                        eventRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        eventRecyclerView.adapter = EventAdapter(interestArray,it, this)
                    }


                }

            }


    }

        else if (event_filter == "school"){
            var play = findViewById(R.id.buttonAdd) as Button
            play.isClickable=false
            play.visibility= View.INVISIBLE // v letter should be capital
            filter_name.text = "Par école"

            optionFilter = findViewById(R.id.spinnerFilter) as Spinner

            var schoolController = SchoolController()
            var optionsSchools= ArrayList<String?>()

            schoolController.getSchools {

                for (school in it) {
                    optionsSchools.add(school.name)
                }
                optionFilter.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsSchools)

                optionFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        school = optionsSchools.get(position)
                    }
                }
            }

            searchButton.setOnClickListener {
                Log.d("TEST SCHOOL","TESTTTT")
                val uid = FirebaseAuth.getInstance().uid ?: ""
                var eventController: EventController = EventController()
                Log.d("TEST SCHOOL",school.toString())

                eventController.FilterEventInterestUser(null, school,arrayListOf(), uid) {
                    var interestArray : ArrayList<Boolean?> = ArrayList<Boolean?>()
                    interestArray = it


                    eventController.FilterSchoolEvent(school) {
                        Log.d("SIZE EVENT SCHOOL", interestArray.size.toString())
                        Log.d("SIZE BOOL SCHOOL", it.size.toString())

                        eventRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        eventRecyclerView.adapter = EventAdapter(interestArray,it, this)
                    }


                }

            }



        }else if(event_filter == "music"){
            filter_name.text = "Par style musical"

            optionFilter = findViewById(R.id.spinnerFilter) as Spinner

            var musicController = MusicController()
            var optionsMusics= ArrayList<String?>()

            musicController.getMusics {

                for (school in it) {
                    optionsMusics.add(school.name)
                }
                optionFilter.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsMusics)

                optionFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        music = optionsMusics.get(position)
                    }
                }
            }

            searchButton.setOnClickListener {
                val uid = FirebaseAuth.getInstance().uid ?: ""
                var eventController: EventController = EventController()

                eventController.FilterEventInterestUser(null, null, arrayMusic, uid) {
                    var interestArray : ArrayList<Boolean?> = ArrayList<Boolean?>()
                    interestArray = it


                    eventController.FilterSchoolEvent(school) {

                        eventRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        eventRecyclerView.adapter = EventAdapter(interestArray,it, this)
                    }


                }

            }

            buttonAdd.setOnClickListener {
                val inflater = LayoutInflater.from(this@EventsActivity)
                val chip_item = inflater.inflate(R.layout.chip_item, null, false) as Chip
                chip_item.text = music
                arrayMusic.add(music.toString())
                chip_item.setOnCloseIconClickListener{view ->
                    chipGroupMusic.removeView(view)
                }
                chipGroupMusic.addView(chip_item)
            }




        }else if(event_filter == "student"){
            filter_name.text = "Par étudiants"

        }




    }
}
