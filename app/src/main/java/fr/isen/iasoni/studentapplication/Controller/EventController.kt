package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.Modele.Event.SubscribeEvent
import fr.isen.iasoni.studentapplication.Modele.Music
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
    fun FindUsersEvent(id_user : String?): ArrayList<String?>{
        val data = database.getReference("Events")
        var userevent : ArrayList<String?> = ArrayList<String?>()
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var event = value.getValue(Event::class.java)!!
                    var subsCon : SubscribeEventController = SubscribeEventController()
                    var subscribe : SubscribeEvent = SubscribeEvent()
                    subsCon.getSubscribeEvent(event.id_subscribe_event) {
                        subscribe = it
                        var users: ArrayList<String>? = subscribe!!.users
                        for(user in users!!){

                            if (user == id_user){
                                userevent.add(event.id_event)
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
return userevent
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

        val data = database.getReference("Events")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        val userController = UserController()
        userController.editEventArray(id_user_admin, newId)
        userController.editEventAdminArray(id_user_admin, newId)

        val event = Event(newId,name, id_user_admin, id_subscribe_event, adresse,zip, city, start_date, end_date, description, limit_user, date)
        data.child(newId).setValue(event)
    }

    fun editEvent(id_event : String?, name : String, adresse: String, zip: String, city: String, start_date: String, end_date: String, description: String, limit_user: Int){

        val data = database.getReference("Events" + id_event)
        var event: Event = Event()
        getEvent(id_event) {
            event = it

            event.name = name
            event.adresse = adresse
            event.zip = zip
            event.city = city
            event.start_date = start_date
            event.end_date = end_date
            event.limit_user = limit_user
            event.description = description
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("name", name)
            childUpdates.put("adresse", adresse)
            childUpdates.put("zip", zip)
            childUpdates.put("city", city)
            childUpdates.put("start_date", start_date)
            childUpdates.put("end_date", end_date)
            childUpdates.put("description", description)
            childUpdates.put("limit_user", limit_user)
            data.updateChildren(childUpdates)


        }



    }



}