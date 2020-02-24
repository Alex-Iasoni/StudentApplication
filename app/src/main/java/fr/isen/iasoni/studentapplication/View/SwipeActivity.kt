package fr.isen.iasoni.studentapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import fr.isen.iasoni.studentapplication.R

class SwipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        //grab all the imagrs and stuff them in our array
        val images: IntArray = intArrayOf(
            R.drawable.alert_icon,
            R.drawable.profil_icon_app,
            R.drawable.home_icon_app,
            R.drawable.passed_event_icon
            )
        //get a reference to the ViewPager in the layout
        val viewPager: ViewPager = findViewById<View>(R.id.pager) as ViewPager
        //Initialize our adapter
        val adapter: PagerAdapter= ImagePagerAdapter(this, images)

        // Binds the adapter to the viewPager
        viewPager.adapter = adapter
    }
}
