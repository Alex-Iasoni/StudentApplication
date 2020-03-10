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

class NotificationController {

    val database = FirebaseDatabase.getInstance()

    fun getNotification(id_notification: String?,callback: (Notification) -> Unit ) {
        var notif : Notification = Notification()
        val myRef = database.getReference("SwipeUser")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
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

    fun addNotif(name:String?,id_event: String?,id_user: String?,id_user_admin : String){
        val data = database.getReference("Musics")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        var exist : Boolean = false
        var notif = Notification(newId,name, id_event,id_user,id_user_admin,date)
        data.child(newId).setValue(notif)

    }

}