package fr.isen.iasoni.studentapplication.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import de.hdodenhof.circleimageview.CircleImageView
import fr.isen.iasoni.studentapplication.Controller.BadgeController
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class SwipeAdapter (var context: Context, private var images: IntArray, val pseudoList: ArrayList<String>): PagerAdapter(){

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = inflater.inflate(R.layout.pager_item, container, false)
        var badgeRecyclerView: RecyclerView
        badgeRecyclerView = itemView.findViewById<View>(R.id.badgeRecyclerView) as RecyclerView

        var badgeList = ArrayList<Badge?>()


        badgeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        var badgeController = BadgeController()
        badgeController.getBadges {
            badgeRecyclerView.adapter = BadgeSwipeAdapter(it, context)
        }
//
//        var badge_buveur = Badge();
//        badge_buveur.img = "badge_sleep"
//        badge_buveur.name= "Buveur"
//        badgeList.add(badge_buveur)
//
//        var badge_gaul = Badge();
//        badge_gaul.img = "badge_disquette"
//        badge_gaul.name= "Gaulé"
//        badgeList.add(badge_gaul)
//
//        var badge_fetard = Badge();
//        badge_fetard.img = "badge_charo"
//        badge_fetard.name= "Fetard"
//        badgeList.add(badge_fetard)
//
//        var badge_again = Badge();
//        badge_again.img = "badge_alcolo"
//        badge_again.name= "A refaire"
//        badgeList.add(badge_again)
//
//
//
//
//        badgeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        badgeRecyclerView.adapter = BadgeProfilAdapter(badgeList, context)



        val image: CircleImageView
        val pseudo: TextView


        //get rederence to ImageView in pager_item layout
        image = itemView.findViewById<View>(R.id.swipeProfile) as CircleImageView
        pseudo = itemView.findViewById<View>(R.id.pseudo) as TextView

        //Set an image to ImageView
        image.setImageResource(images[position])
        pseudo.text = pseudoList[position]


        //Add pager_item layout as the current page to ViewPager
        (container as ViewPager).addView(itemView)
        return itemView


    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //Remove pager_item layout to ViewPager
        (container as ViewPager).removeView(`object` as ConstraintLayout)
    }

}