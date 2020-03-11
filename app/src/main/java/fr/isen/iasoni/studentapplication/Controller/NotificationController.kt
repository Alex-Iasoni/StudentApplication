package fr.isen.iasoni.studentapplication.Controller

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.Notification
import fr.isen.iasoni.studentapplication.Modele.User.SwipeUser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationController {

    val database = FirebaseDatabase.getInstance()

    fun getNotification(id_notification: String?,callback: (Notification) -> Unit ) {

        val myRef = database.getReference("Notification")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var notif : Notification = Notification()
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_notification)){

                        notif  = value.getValue(Notification::class.java)!!

                    }
                }
                callback.invoke(notif)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun DateCurrent() : String{
        var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        return dateString
    }

    fun addNotif(name:String?,id_event: String?,id_user: String?){
        val data = database.getReference("Notification")
        val newId = data.push().key.toString()
        val date = DateCurrent()

        var notif = Notification(newId,name,id_event,id_user,date)
        data.child(newId).setValue(notif)

    }

    fun FindNotifUser(id_user: String?,callback: (ArrayList<Notification>) -> Unit){
        val myRef = database.getReference("Notification")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var notifList : ArrayList<Notification> = ArrayList<Notification> ()
                var notif : Notification = Notification()
                for (value in dataSnapshot.children){
                    var notif: Notification =  value.getValue(Notification::class.java)!!
                    var count = 0
                    if(notif.id_user.equals(id_user)){
                        count++
                        if(count == 1){
                            notifList.add(notif)
                        }



                    }

                }

                callback.invoke(notifList)
            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })


    }







}