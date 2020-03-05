package fr.isen.iasoni.studentapplication.Adapters

import fr.isen.iasoni.studentapplication.R


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.View.EventInfoActivity
import kotlinx.android.synthetic.main.recycler_view_event_cell.view.*


class EventAdapter (val interrested: ArrayList<Boolean>, val events: ArrayList<Event>, val context: Context): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_event_cell, parent, false)
        return EventViewHolder(interrested,view, context)
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event,position)


    }



    class EventViewHolder(val interrested: ArrayList<Boolean>, val view: View, val context: Context): RecyclerView.ViewHolder(view) {
        fun bind(event: Event, position: Int) {
            view.eventDisplayTitleView.text = event.name
            view.eventDisplayContentView.text = event.description


            val star_full = "star_full"
            val star_empty = "star_empty"
            if(interrested[position]){
                val resID_full = context.getResources().getIdentifier(star_full, "drawable", "fr.isen.iasoni.studentapplication")
                view.eventDisplayImageView.setImageResource(resID_full)

            }else{
                val resID_empty = context.getResources().getIdentifier(star_empty, "drawable", "fr.isen.iasoni.studentapplication")
                view.eventDisplayImageView.setImageResource(resID_empty)

            }
            var change_interrested: Boolean = interrested[position]

            view.eventDisplayImageView.setOnClickListener {

                change_interrested = !change_interrested
                if(change_interrested){
                    //etoile appuyé
                    val resID_full = context.getResources().getIdentifier(star_full, "drawable", "fr.isen.iasoni.studentapplication")
                    view.eventDisplayImageView.setImageResource(resID_full)

                }else{
                    //etoile nonappuyé
                    val resID_empty = context.getResources().getIdentifier(star_empty, "drawable", "fr.isen.iasoni.studentapplication")
                    view.eventDisplayImageView.setImageResource(resID_empty)

                }

            }

            view.eventDisplayInfoView.setOnClickListener{
                val foo = Intent(context, EventInfoActivity::class.java)
                foo.putExtra("idEvent", event.id_event)


                context.startActivity(foo)

            }



        }



    }



}
