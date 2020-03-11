package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Adapters.EventAdapter
import fr.isen.iasoni.studentapplication.Adapters.PassedEventsAdapter
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_passed_events.*
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.activity_profil.navigation_view
import io.grpc.Deadline.after
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import fr.isen.iasoni.studentapplication.Controller.SubscribeEventController
import fr.isen.iasoni.studentapplication.Modele.Event.SubscribeEvent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PassedEventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passed_events)

        //---------------Navigation Menu-------------------------------------------
        navigation_view.setSelectedItemId(R.id.action_events);
        navigation_view.setOnNavigationItemSelectedListener {item ->
            var activity = ""
            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "passedEventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"
            }
            Toast.makeText(this@PassedEventsActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()
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

//        for (current_event in it){
//            val sdf = SimpleDateFormat("dd/MM/yyyy")
//            val event_date = sdf.parse(current_event?.end_date)
//            if (Date().after(event_date)) {
//                if (current_event != null) {
//                    eventPassed.add(current_event)
//                }
//            }
//        }
        var eventController = EventController()
        val database = FirebaseDatabase.getInstance()
        val uid = FirebaseAuth.getInstance().uid ?: ""


        val data = database.getReference("Events")
        eventController.getEvents{
            var size_event: Int = it.size
            var count: Int = 0
            var userevent : ArrayList<Event> = ArrayList<Event>()
            var events : ArrayList<Event?> = ArrayList<Event?>()
            events = it
            for(event in events ){
                var user_find: Boolean = false

                var subsCon : SubscribeEventController = SubscribeEventController()
                var subscribe : SubscribeEvent = SubscribeEvent()
                subsCon.getSubscribeEvent(event!!.id_subscribe_event) {
                    subscribe = it
                    var users: ArrayList<String> = subscribe.users
                    for (user in users) {

                        if (user == uid) {
                            Log.d("User Find!","FIND")
                            val sdf = SimpleDateFormat("dd/MM/yyyy")
                            val event_date = sdf.parse(event?.end_date)
                            Log.d("User DATE NOT GOOD!",event?.end_date)

                            if (Date().after(event_date)) {

                                userevent.add(event)

                            }
                            count++
                            user_find = true

                        }

                    }
                    if (user_find == false) {
                        count++
                    }
                    if (count == size_event){

                        notifRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        notifRecyclerView.adapter = PassedEventsAdapter(userevent, this)
                    }

                }
            }
        }


    }
}
