package fr.isen.iasoni.studentapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import de.hdodenhof.circleimageview.CircleImageView
import fr.isen.iasoni.studentapplication.R

class SwipeAdapter (var context: Context, private var images: IntArray, val pseudoList: ArrayList<String>): PagerAdapter(){

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val image: CircleImageView
        val pseudo: TextView
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = inflater.inflate(R.layout.pager_item, container, false)

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