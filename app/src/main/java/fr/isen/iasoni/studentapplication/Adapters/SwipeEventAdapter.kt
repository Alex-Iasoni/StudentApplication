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
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import fr.isen.iasoni.studentapplication.Controller.BadgeController
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.User.User
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_profil.*

class SwipeEventAdapter (var context: Context, var arrayListUser: ArrayList<User>): PagerAdapter(){

    override fun getCount(): Int {
        return arrayListUser.size
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
            badgeRecyclerView.adapter = BadgeSwipeEventAdapter(arrayListUser[position].id_user.toString(),it, context)
        }


        val image: CircleImageView
        val pseudo: TextView


        //get rederence to ImageView in pager_item layout
        image = itemView.findViewById<View>(R.id.swipeProfile) as CircleImageView
        pseudo = itemView.findViewById<View>(R.id.pseudo) as TextView

        if(arrayListUser[position].img_profil!= "none" && arrayListUser[position].img_profil != null) {
            Glide.with(context).load(arrayListUser[position].img_profil).into(image)
        }

        //Set an image to ImageView
        pseudo.text = arrayListUser[position].name + " " + arrayListUser[position].surname


        //Add pager_item layout as the current page to ViewPager
        (container as ViewPager).addView(itemView)
        return itemView


    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //Remove pager_item layout to ViewPager
        (container as ViewPager).removeView(`object` as ConstraintLayout)
    }

}