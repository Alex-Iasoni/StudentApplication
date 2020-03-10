package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.City
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.Modele.Event.SubscribeEvent
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.User.User
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.time.format.DateTimeFormatter

import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EventController {
    val database = FirebaseDatabase.getInstance()


    fun getEvents(callback: (ArrayList<Event?>) -> Unit ) {

        val myRef = database.getReference("Events")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var events : ArrayList<Event?> = ArrayList<Event?>()

                for (value in dataSnapshot.children){
                var event : Event? = Event()
                    event = value.getValue(Event::class.java)

                        events.add(event)

                }

                callback.invoke(events)
            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
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

    fun VerifiedEtudiantSchool(id_event: String?, callback: (String) -> Unit){
    var etudiant = "false"
        var event : Event = Event()
        getEvent(id_event){
            event = it
         if( event.etudiant.equals("true")){
             etudiant = "true"

            }else{
             etudiant = "false"
         }
callback.invoke(etudiant)
        }


    }
    fun NotifEvent(id_event: String?){
        val data = database.getReference("Events")
        var userevent : ArrayList<String?> = ArrayList<String?>()
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun GetUserEvent(id_user : String?,callback: (ArrayList<Event>) -> Unit ){
        val data = database.getReference("Events")
        var userevent : ArrayList<Event> = ArrayList<Event>()
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var event = value.getValue(Event::class.java)!!
                    var subsCon : SubscribeEventController = SubscribeEventController()
                    var subscribe : SubscribeEvent = SubscribeEvent()
                    subsCon.getSubscribeEvent(event.id_subscribe_event) {
                        subscribe = it
                        var users: ArrayList<String> = subscribe.users
                        for(user in users){

                            if (user == id_user){
                                userevent.add(event)
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

fun  Interest(id_user: String, id_event: String){
        var user : UserController = UserController()
        user.editEventArray(id_user, id_event)
        var event : Event = Event()
        getEvent(id_event){
            var interest = false
            event = it
            var user : UserController = UserController()
            LimitUser(it.id_event,it.id_subscribe_event){

           if(!it){
               interest = true
               user.UserCertified(id_user) {
                   if (it.equals("false") && event.etudiant.equals("false")) {
                       var subs: SubscribeEventController = SubscribeEventController()
                       subs.addUserOnEvent(event.id_subscribe_event, id_user)

                   } else if (it.equals("true") and event.etudiant.equals("true")) {
                       var subs: SubscribeEventController = SubscribeEventController()
                       subs.addUserOnEvent(event.id_subscribe_event, id_user)
                   } else if (it.equals("true") and event.etudiant.equals("false")) {
                       var subs: SubscribeEventController = SubscribeEventController()
                       subs.addUserOnEvent(event.id_subscribe_event, id_user)
                   }
               }
           }

            }

        }
    }

    fun  UnInterest(id_user: String, id_event: String  ){
        var user : UserController = UserController()
        user.editEventArray(id_user, id_event)
        var event : Event = Event()
        getEvent(id_event){
            event = it
            var user : UserController = UserController()
            user.UserCertified(id_user){
                if(it.equals("false") && event.etudiant.equals("false")){
                    var subs : SubscribeEventController = SubscribeEventController()
                    subs.deleteUseronEvent(event.id_subscribe_event, id_user)
                }
                else if(it.equals("true") and  event.etudiant.equals("true")){
                    var subs : SubscribeEventController = SubscribeEventController()
                    subs.deleteUseronEvent(event.id_subscribe_event, id_user)
                }
                else if(it.equals("true") and  event.etudiant.equals("false")){
                    var subs : SubscribeEventController = SubscribeEventController()
                    subs.deleteUseronEvent(event.id_subscribe_event, id_user)
                }
            }
        }
    }

    fun LimitUser(id_event: String?, id_subscribe_event: String, callback: (Boolean) -> Unit){

       var subs : SubscribeEventController = SubscribeEventController()
subs.NumberSubscribeUser(id_subscribe_event){
    var number : Int = it
    var limit = true
    getEvent(id_event){
        if(it.limit_user!!.toInt() >= number){
            limit  = false
        }else{

            limit = true
            val data = database.getReference("Events/" + id_event)
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("complete", limit)
            data.updateChildren(childUpdates)

        }
        callback.invoke(limit)
    }
}

    }

    fun FilterMusicEvent(musics: ArrayList<String?>, callback: (ArrayList<Event?>) -> Unit){
        var eventsToReturn = ArrayList<Event?>()



                var musicController = MusicController()
                for(music in musics) {
                    musicController.getIdMusic(music) {

                        var eventfilter: ArrayList<Event?> = ArrayList<Event?>()
                        var current_id_music = it;
                        var eventC = EventController()
                        eventC.getEvents {

                            for (event in it) {
                                if (event != null) {
                                    var de : ArrayList<String>? = event.id_music
                                    if (de != null) {
                                        for (musicid in de){
                                            if (musicid.toString() == current_id_music.toString()) {
                                                eventsToReturn.add(event)
                                            }
                                        }
                                    }

                                }
                            }


                        }
                        SortbyStartDateEvent(eventsToReturn)

                        callback.invoke(eventsToReturn)
                    }
                }




    }

    fun FilterSchoolEvent(school: String?, callback: (ArrayList<Event?>) -> Unit){




                var shoolcontroller = SchoolController()
                shoolcontroller.getIdSchool(school) {
                    var eventsToReturn = ArrayList<Event?>()

                    var eventfilter : ArrayList<Event?> = ArrayList<Event?>()
                    var current_id_school = it;
                    var eventC = EventController()
                    eventC.getEvents {

                        for(event in it){
                            if (event != null) {
                                if(event.id_school.toString() == current_id_school.toString()) {
                                    eventsToReturn.add(event)
                                }
                            }
                        }
                        SortbyStartDateEvent(eventsToReturn)
                        callback.invoke(eventsToReturn)
                    }
                }



            }





    fun FilterCityEvent(city: String?, callback: (ArrayList<Event?>) -> Unit){


                var cityController = CityController()
                cityController.getIdCity(city) {
                    var eventsToReturn = ArrayList<Event?>()
                    var eventfilter : ArrayList<Event?> = ArrayList<Event?>()
                    var current_id_city = it;
                    var eventC = EventController()
                    eventC.getEvents {

                        for(event in it){
                            if (event != null) {
                                if(event.id_city.toString() == current_id_city.toString()) {
                                    eventsToReturn.add(event)
                                }
                            }
                        }
                        SortbyStartDateEvent(eventsToReturn)
                        callback.invoke(eventsToReturn)

                    }
                }




    }



    fun FilterEventInterestUser(city: String?, school : String?, musics: ArrayList<String?>,id_user : String,callback: (ArrayList<Boolean?>) -> Unit) {

        val data = database.getReference("Events")

        var events: ArrayList<Event?> = ArrayList<Event?>()

        if (city != null) {

            FilterCityEvent(city) {
                var interest: ArrayList<Boolean?> = ArrayList<Boolean?>()
                for (event in it) {

                    var subs: SubscribeEvent? = SubscribeEvent()
                    var subsCon: SubscribeEventController = SubscribeEventController()

                    if (event != null) {
                        subsCon.getSubscribeEvent(event.id_subscribe_event) {
                            var user_find: Boolean = false
                            for (subsuser in it.users) {
                                if (subsuser.equals(id_user)) {
                                    interest.add(true)
                                    user_find = true
                                }

                            }

                            if(user_find == false){
                                interest.add(false)
                            }
                            Log.d("ddeeeee",interest.toString())
                            callback.invoke(interest)
                        }
                    }


                }


            }

        }
        else if(school != null){
            FilterSchoolEvent(school) {
                events = it
                var interest: ArrayList<Boolean?> = ArrayList<Boolean?>()
                for (event in events) {
                    var subs: SubscribeEvent? = SubscribeEvent()
                    var subsCon: SubscribeEventController = SubscribeEventController()

                    if (event != null) {
                        subsCon.getSubscribeEvent(event.id_subscribe_event) {
                            var user_find: Boolean = false
                            for (subsuser in it.users) {
                                if (subsuser.equals(id_user)) {
                                    interest.add(true)
                                    user_find = true
                                }

                            }
                            if(user_find == false){
                                interest.add(false)
                            }
                            callback.invoke(interest)

                        }
                    }
                }


            }

        }else if (musics.size != 0){
            FilterMusicEvent(musics) {
                events = it
                var interest: ArrayList<Boolean?> = ArrayList<Boolean?>()
                for (event in events) {
                    var subs: SubscribeEvent? = SubscribeEvent()
                    var subsCon: SubscribeEventController = SubscribeEventController()

                    if (event != null) {
                        subsCon.getSubscribeEvent(event.id_subscribe_event) {
                            var user_find: Boolean = false
                            for (subsuser in it.users) {
                                if (subsuser.equals(id_user)) {
                                    interest.add(true)
                                    user_find = true
                                }

                            }
                            if(user_find == false){
                                interest.add(false)

                            }

                            callback.invoke(interest)
                        }
                    }
                }


            }

        }

    }





    fun createEvent(name : String?, id_user_admin: String?, adresse: String?, zip: String?, city: String?, school : String?, musics : ArrayList<String>, start_date: String?, end_date: String?, description: String?, etudiant : String, limit_user: String?){

        val data = database.getReference("Events")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        val userController = UserController()

        userController.editEventArray(id_user_admin, newId)
        userController.editEventAdminArray(id_user_admin, newId)


        var schoolController = SchoolController()

        schoolController.getIdSchool(school) {
            var id_school: String? = ""
            id_school = it

            schoolController.editEventArray(id_school, newId)


            var cityController = CityController()
            cityController.getIdCity(city) {
                var id_city: String? = ""
                id_city = it
                cityController.editEventArray(it, newId)

                FindMusic(newId,musics){
                    var id_musics : ArrayList<String>? = ArrayList<String>()
                    id_musics = it



                    val newId2 = data.push().key.toString()

        val event = Event(newId,name, id_user_admin, newId2, adresse,zip, id_city, id_school, id_musics,start_date, end_date, description,etudiant, limit_user!!.toInt(), date,"false")
        data.child(newId).setValue(event)
                    }
                }
                }

    }
fun FindMusic(newId : String,musics : ArrayList<String>, callback: (ArrayList<String>?) -> Unit){
    var musicController = MusicController()
    var id_musics: ArrayList<String> = ArrayList<String>()
    for (music in musics) {
        musicController.getIdMusic(music) {

            if (it != null) {
                id_musics.add(it)
            }
            musicController.editEventArray(it, newId)

        }

    }
    callback.invoke(id_musics)
}

    fun editEvent(id_event : String?, name : String, adresse: String, zip: String, start_date: String, end_date: String, description: String, limit_user: Int){

        val data = database.getReference("Events/" + id_event)
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

    fun FindIdEvent(name: String, id_user_admin: String?, callback: (String?) -> Unit){

        var id_event : String? = ""
        val myRef = database.getReference("Events")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var event : Event  =  value.getValue(Event::class.java)!!
                    if(event.name.equals(name) && event.id_user_admin.equals(id_user_admin)){

                        id_event = event.id_event
                        break;
                    }
                }
                callback.invoke(id_event)
            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })


    }





}