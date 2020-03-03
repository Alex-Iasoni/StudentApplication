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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    fun VerifiedEtudiantSchool(id_event: String?) : Boolean{
    var etudiant = false
        var event : Event = Event()
        getEvent(id_event){
            event = it
         if(event.etudiant == true){
             etudiant = true

            }else{
             etudiant = false
         }
        }

   return etudiant
    }

    fun FindUsersEvent(id_user : String?,callback: (ArrayList<String?>) -> Unit ){
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
                callback.invoke(userevent)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun DateCurrent() : String{
    var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        return dateString
    }
    fun SortbyStartDateEvent(event : ArrayList<Event?>){
        event.sortByDescending { it!!.start_date }


}

//    fun FindEvent(, callback: (ArrayList<Event?>) -> Unit){
//        val data = database.getReference("Events")
//        var eventfilter : ArrayList<Event?> = ArrayList<Event?>()
//        data.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (value in dataSnapshot.children){
//                    var event = value.getValue(Event::class.java)!!
//                        var eventmod: Event = Event()
//                        getEvent(event.id_event){
//                            eventmod = it
//                            if(eventmod.type.equals(type)){
//                                eventfilter.add(eventmod)
//                            }
//
//                        }
//
//                }
//                SortbyStartDateEvent(eventfilter)
//                callback.invoke(eventfilter)
//            }
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//
//
//
//    }
//
//


    fun  Interest(id_user: String, id_event: String){
        var user : UserController = UserController()
        user.editEventArray(id_user, id_event)
        var event : Event = Event()
        getEvent(id_event){
            event = it
            var subs : SubscribeEventController = SubscribeEventController()
            subs.addUserOnEvent(event.id_subscribe_event, id_user)
        }


    }
//
//    fun InterestByFilter(type: String?,id_user : String?): ArrayList<Boolean?>{
//
//        val data = database.getReference("Events")
//        var interest : ArrayList<Boolean?> = ArrayList<Boolean?>()
//        var events : ArrayList<Event?> = ArrayList<Event?>()
//        FindEventFilters(type){
//            events = it
//            for (event in events){
//                var subs : SubscribeEvent? = SubscribeEvent()
//                var subsCon : SubscribeEventController = SubscribeEventController()
//
//                subsCon.getSubscribeEvent(event!!.id_subscribe_event){
//                    subs = it
//                    var subsusers : ArrayList<String?> =  ArrayList<String?>()
//                   for(subsuser in subsusers){
//                       if(subsuser.equals(id_user)){
//                           interest.add(true)
//                       }else{
//                           interest.add(false)
//                       }
//
//                   }
//
//                }
//
//
//            }
//        }
//    return interest
//
//    }


    fun createEvent(name : String?, id_user_admin: String?, id_subscribe_event: String?, adresse: String?, zip: String?, city: String?, school : String?, musics : ArrayList<String>, start_date: String?, end_date: String?, description: String?, etudiant : Boolean?, limit_user: Int?){

        val data = database.getReference("Events")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        val userController = UserController()
        var id_musics : ArrayList<String>? = ArrayList<String>()
        var id_school : String? = ""
        var id_city: String? = ""
        userController.editEventArray(id_user_admin, newId)
        userController.editEventAdminArray(id_user_admin, newId)

        var schoolController = SchoolController()

        schoolController.getIdSchool(school){
            id_school = it
            schoolController.editEventArray(it, newId)
        }


        var cityController = CityController()
    cityController.getIdCity(city){
        id_city = it
        cityController.editEventArray(it,newId)
    }

        var musicController = MusicController()
        for(music in musics){
            musicController.getIdMusic(music){
                if (it != null) {
                    id_musics!!.add(it)
                }
                musicController.editEventArray(it, newId)
            }
        }



        val event = Event(newId,name, id_user_admin, id_subscribe_event, adresse,zip, id_city, id_school, id_musics,start_date, end_date, description,etudiant, limit_user, date)
        data.child(newId).setValue(event)

    }


    fun editEvent(id_event : String?, name : String, adresse: String, zip: String, start_date: String, end_date: String, description: String, limit_user: Int){

        val data = database.getReference("Events" + id_event)
        var event: Event = Event()
        getEvent(id_event) {
            event = it

            event.name = name
            event.adresse = adresse
            event.zip = zip

            event.start_date = start_date
            if(end_date.compareTo(DateCurrent()) > 0 ) {
                event.end_date = end_date
            }

            event.limit_user = limit_user
            event.description = description
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("name", name)
            childUpdates.put("adresse", adresse)
            childUpdates.put("zip", zip)

            childUpdates.put("start_date", start_date)
            childUpdates.put("end_date", end_date)
            childUpdates.put("description", description)
            childUpdates.put("limit_user", limit_user)
            data.updateChildren(childUpdates)


        }



    }



}