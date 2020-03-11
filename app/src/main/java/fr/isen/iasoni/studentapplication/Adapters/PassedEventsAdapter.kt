package fr.isen.iasoni.studentapplication.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.R
import fr.isen.iasoni.studentapplication.View.EventInfoActivity
import fr.isen.iasoni.studentapplication.View.SwipeActivity
import kotlinx.android.synthetic.main.recycler_view_event_cell.view.*

class PassedEventsAdapter (val events: ArrayList<Event>, val context: Context): RecyclerView.Adapter<PassedEventsAdapter.EventViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_passed_event_cell, parent, false)
        return EventViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event,position)


    }



    class EventViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {
        fun bind(event: Event?, position: Int) {
            view.eventDisplayTitleView.text = event?.name
            view.eventDisplayContentView.text = event?.description
            val uid = FirebaseAuth.getInstance().uid ?: ""
            var eventController = EventController()


            val star_full = "star_full"
            val star_empty = "star_empty"


            view.eventDisplayInfoView.setOnClickListener{
                val foo = Intent(context, SwipeActivity::class.java)
                if (event != null) {
                    foo.putExtra("idEvent", event.id_event)
                    context.startActivity(foo)
                }

            }



        }



    }



}
