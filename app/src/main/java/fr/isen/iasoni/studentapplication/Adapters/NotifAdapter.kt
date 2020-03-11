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
import fr.isen.iasoni.studentapplication.Modele.Notification
import fr.isen.iasoni.studentapplication.R
import fr.isen.iasoni.studentapplication.View.EventInfoActivity
import kotlinx.android.synthetic.main.recycler_view_event_cell.view.*
import kotlinx.android.synthetic.main.recycler_view_notif_cell.view.*

class NotifAdapter (val notifs: ArrayList<Notification>, val context: Context): RecyclerView.Adapter<NotifAdapter.NotifViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_notif_cell, parent, false)
        return NotifViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return notifs.count()
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val notif = notifs[position]
        holder.bind(notif,position)


    }



    class NotifViewHolder(val view: View, val context: Context): RecyclerView.ViewHolder(view) {
        fun bind(notif: Notification?, position: Int) {
            view.notifDisplayContentView.text = notif?.name


            view.layoutDisplayNotifView.setOnClickListener{
                val foo = Intent(context, EventInfoActivity::class.java)
                if (notif != null) {
                    foo.putExtra("idEvent", notif.id_event)
                    context.startActivity(foo)
                }

            }

        }



    }



}
