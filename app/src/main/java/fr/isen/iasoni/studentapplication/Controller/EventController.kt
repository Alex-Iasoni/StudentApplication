package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.Modele.User.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventController {
    val database = FirebaseDatabase.getInstance()


    fun getEvent(id_event: String?,callback: (Event) -> Unit ) {
        var event : Event = Event()
        val myRef = database.getReference("Events")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_event)){

                        event  = value.getValue(Event::class.java)!!

                    }
                }
                callback.invoke(event)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun DateCurrent() : String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createEvent(name : String?, id_user_admin: String?, id_subscribe_event: String?, adresse: String?, zip: String?, city: String?, start_date: String?, end_date: String?, description: String?, limit_user: Int?){

        val dataPost = database.getReference("Events")
        val newId = dataPost.push().key.toString()
        val date = DateCurrent()
       //editPostArray(newId,user_id)

        val event = Event(newId,name, id_user_admin, id_subscribe_event, adresse,zip, city, start_date, end_date, description, limit_user, date)
        dataPost.child(newId).setValue(event)
    }

    fun InitEvent(){

    }

}