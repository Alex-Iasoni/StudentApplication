package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import fr.isen.iasoni.studentapplication.Adapters.SwipeAdapter
import fr.isen.iasoni.studentapplication.Adapters.SwipeEventAdapter
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Controller.SubscribeEventController
import fr.isen.iasoni.studentapplication.Controller.UserController
import fr.isen.iasoni.studentapplication.Modele.User.User
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class SwipeActivity : AppCompatActivity() {

    var swipe_event: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        //----------------------Menu Navigation-----------------------------
        navigation_view.setSelectedItemId(R.id.action_swipe);
        navigation_view.setOnNavigationItemSelectedListener {item ->
            var activity = ""
            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "passedEventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"
            }
            Toast.makeText(this@SwipeActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()
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
        //--------------------------------------------------------------------------

        swipe_event = intent.getStringExtra("idEvent") //filtre de l'event
        var user_array = ArrayList<User>()
        var eventController = EventController()
        eventController.getEvent(swipe_event){

            var subscribeEventController = SubscribeEventController()
            subscribeEventController.getSubscribeEvent(it.id_subscribe_event){
                var userController = UserController()
                var count = 0
                var count_size = it.users.size
                for(user_id in it.users) {
                    userController.getUser(user_id) {
                        user_array.add(it)
                        count++
                        if(count == count_size){
                            //get a reference to the ViewPager in the layout
                            val viewPager: ViewPager = findViewById<View>(R.id.pager) as ViewPager
                            //Initialize our adapter
                            val adapter: PagerAdapter= SwipeEventAdapter(this, user_array)

                            // Binds the adapter to the viewPager
                            viewPager.adapter = adapter
                        }
                    }
                }
            }

        }
        //grab all the imagrs and stuff them in our array
        if(swipe_event == ""){
            val images: IntArray = intArrayOf(
                R.drawable.louism,
                R.drawable.iasoni,
                R.drawable.berton,
                R.drawable.ilane
            )

            val pseudoList = ArrayList<String>()
            pseudoList.add("Louis Gnolfi")
            pseudoList.add("Alexandre Iasoni")
            pseudoList.add("Thomas Berton")
            pseudoList.add("Ilane Lopez")


            //get a reference to the ViewPager in the layout
            val viewPager: ViewPager = findViewById<View>(R.id.pager) as ViewPager
            //Initialize our adapter
            val adapter: PagerAdapter= SwipeAdapter(this, images, pseudoList)

            // Binds the adapter to the viewPager
            viewPager.adapter = adapter
            }
        }

}
