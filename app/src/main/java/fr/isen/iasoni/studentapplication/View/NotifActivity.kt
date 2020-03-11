package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import fr.isen.iasoni.studentapplication.Adapters.NotifAdapter
import fr.isen.iasoni.studentapplication.Adapters.PassedEventsAdapter
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Controller.NotificationController
import fr.isen.iasoni.studentapplication.Controller.SubscribeEventController
import fr.isen.iasoni.studentapplication.Controller.UserController
import fr.isen.iasoni.studentapplication.Modele.Event.SubscribeEvent
import fr.isen.iasoni.studentapplication.Modele.Notification
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_passed_events.*
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.activity_profil.navigation_view

class NotifActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)



        //-------------Navigation menu----------------------------
        navigation_view.setSelectedItemId(R.id.action_notification);
        navigation_view.setOnNavigationItemSelectedListener {item ->

            var activity = ""
            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "passedEventsActivity"
                R.id.action_events -> activity = "EventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"
            }
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
            if(activity == "EventsActivity"){
                startActivity(Intent(this, PassedEventsActivity::class.java))
            }
            return@setOnNavigationItemSelectedListener true
        }
        //--------------------------------------------------------------


        var subs : SubscribeEventController = SubscribeEventController()
        val uid = FirebaseAuth.getInstance().uid ?: ""
  var user : UserController = UserController()
        var no : NotificationController = NotificationController()
        var name = ""
        no.FindNotifUser(uid){
            notifRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            notifRecyclerView.adapter = NotifAdapter(it, this)


            subs.FindSubsbyUser(uid){
                var event : EventController = EventController()
                for(id_subs in it)
                event.getIdEventBySubs(id_subs.id_subscribe_event){
                    var notif : NotificationController = NotificationController()
                    notif.addNotif("Test",it,uid)
                }


        }
        }




    }


}
