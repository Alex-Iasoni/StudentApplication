package fr.isen.iasoni.studentapplication.Adapters

import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.recycler_view_badge_cell.view.*




import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class BadgeProfilAdapter (val badges: ArrayList<Badge?>, val context: Context): RecyclerView.Adapter<BadgeProfilAdapter.BadgeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_badge_cell, parent, false)
        return BadgeViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return badges.count()
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val badge = badges[position]
        holder.bind(badge)


    }



    class BadgeViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {
        fun bind(badge: Badge?) {
            val mDrawableName = badge?.img
            val resID = context.getResources().getIdentifier(mDrawableName, "drawable", "fr.isen.iasoni.studentapplication")

            view.badgeDisplayImageView.setImageResource(resID)

        }



    }



}
