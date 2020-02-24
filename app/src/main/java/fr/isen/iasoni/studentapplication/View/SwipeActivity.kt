package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import fr.isen.iasoni.studentapplication.Adapters.ImagePagerAdapter
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class SwipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)


        navigation_view.setSelectedItemId(R.id.action_swipe);


        navigation_view.setOnNavigationItemSelectedListener {item ->

            var activity = ""

            when(item.itemId){
                R.id.action_home -> activity = "Home"
                R.id.action_notification -> activity = "Notification"
                R.id.action_profil -> activity = "ProfilActivity"
                R.id.action_events -> activity = "EventsActivity"
                R.id.action_swipe -> activity = "SwipeActivity"


            }
            Toast.makeText(this@SwipeActivity, "$activity clicked!", Toast.LENGTH_SHORT).show()

            if(activity == "ProfilActivity"){

                startActivity(Intent(this, ProfilActivity::class.java))
            }
            if(activity == "SwipeActivity"){
                startActivity(Intent(this, SwipeActivity::class.java))
            }
            return@setOnNavigationItemSelectedListener true
        }





        //grab all the imagrs and stuff them in our array
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
        val adapter: PagerAdapter=
            ImagePagerAdapter(this, images, pseudoList)

        // Binds the adapter to the viewPager
        viewPager.adapter = adapter
    }
}
