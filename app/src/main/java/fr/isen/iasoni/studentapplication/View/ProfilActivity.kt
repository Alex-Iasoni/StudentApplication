package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Adapters.BadgeProfilAdapter
import fr.isen.iasoni.studentapplication.Controller.*
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.City
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    fun switchPageDisconnect(view: View){

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth?.currentUser
        val imageView = findViewById<ImageView>(R.id.profilePicture)
        var userContollerImg = UserController()
        userContollerImg.getUser(mAuth.uid){
            if(it.img_profil != "none" && it.img_profil != null) {
                Glide.with(this).load(it.img_profil).into(imageView)
            }

        }
        edit_profil.setOnClickListener {
            startActivity(Intent(this, EditProfilActivity::class.java))
        }


        var userController = UserController()
        userController.getUser(user!!.uid){
            userSurname.text = it.name +" "+ it.surname

            var schoolController = SchoolController()
            schoolController.getSchool(it.id_school){
                schoolName.text = it.name
            }

            var cityController = CityController()
            cityController.getCity(it.id_city){
                nameCity.text = it.name
            }

        }

        navigation_view.setSelectedItemId(R.id.action_profil);


        navigation_view.setOnNavigationItemSelectedListener {item ->

            var activity = ""

            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "passedEventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"
            }
            Toast.makeText(this@ProfilActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()

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

        var badgeList = ArrayList<Badge?>()


        var userControllerBadge = UserController()
        userControllerBadge.getUser(mAuth.uid){
            var badgeController = BadgeController()
            var count: Int = 0
            var size_badge: Int = it.badges.size
            for (badge in it.badges){
                badgeController.getBadge(badge){
                    badgeList.add(it)
                    count++
                    if (count == size_badge){
                        badgeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        badgeRecyclerView.adapter = BadgeProfilAdapter(badgeList, this)
                    }

                }
            }
        }





    }
}
