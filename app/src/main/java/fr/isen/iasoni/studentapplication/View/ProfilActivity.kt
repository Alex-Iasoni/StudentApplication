package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.iasoni.studentapplication.Adapters.BadgeProfilAdapter
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

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

        var badgeList = ArrayList<Badge>()

        var badge_buveur = Badge();
        badge_buveur.img = "buveur"
        badge_buveur.name= "Buveur"
        badgeList.add(badge_buveur)

        var badge_gaul = Badge();
        badge_gaul.img = "bien_gaule"
        badge_gaul.name= "Gaulé"
        badgeList.add(badge_gaul)

        var badge_fetard = Badge();
        badge_fetard.img = "fetard"
        badge_fetard.name= "Fetard"
        badgeList.add(badge_fetard)

        var badge_again = Badge();
        badge_again.img = "again"
        badge_again.name= "A refaire"
        badgeList.add(badge_again)


        badgeList.add(badge_buveur)

        badgeList.add(badge_gaul)

        badgeList.add(badge_fetard)


        badgeList.add(badge_again)



        badgeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        badgeRecyclerView.adapter = BadgeProfilAdapter(badgeList, this)

    }
}
