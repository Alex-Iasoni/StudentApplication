package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import android.util.Log
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
                        var users: ArrayList<String> = subscribe.users
                        for(user in users){

                            if (user == id_user){
                                userevent.add(event.id_event)
                            }
                        }
                        callback.invoke(userevent)
                    }
                }

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
            event = it
            var user : UserController = UserController()
            user.UserCertified(id_user){
                if(it.equals("false") && event.etudiant.equals("false")){
                    var subs : SubscribeEventController = SubscribeEventController()
                    subs.addUserOnEvent(event.id_subscribe_event, id_user)
                }
                else if(it.equals("true") and  event.etudiant.equals("true")){
                    var subs : SubscribeEventController = SubscribeEventController()
                    subs.addUserOnEvent(event.id_subscribe_event, id_user)
                }
                else if(it.equals("true") and  event.etudiant.equals("false")){
                    var subs : SubscribeEventController = SubscribeEventController()
                    subs.addUserOnEvent(event.id_subscribe_event, id_user)
                }
            }
        }
    }

    fun FilterMusicEvent(musics: ArrayList<String?>, callback: (ArrayList<Event?>) -> Unit){

        val data = database.getReference("Events")
        var eventfilter : ArrayList<Event?> = ArrayList<Event?>()
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var event = value.getValue(Event::class.java)!!
                    var eventmod: Event = Event()
                    getEvent(event.id_event){

                        eventmod = it
                        for(music in musics){
                            var musicController = MusicController()
                            musicController.getIdMusic(music){

                                musicController.getMusic(it){
                                    var music_id : String? = it.id_music
                                    var event_musicid : ArrayList<String>? =  eventmod.id_music
                                    if (event_musicid != null) {
                                        for(id_music in event_musicid){
                                            if(music_id == id_music){
                                                eventfilter.add(eventmod)
                                            }

                                        }
                                    }


                                }
                            }


                        }



                    }

                }
                SortbyStartDateEvent(eventfilter)
                callback.invoke(eventfilter)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    fun FilterSchoolEvent(school: String?, callback: (ArrayList<Event?>) -> Unit){

        val data = database.getReference("Events")
        var eventfilter : ArrayList<Event?> = ArrayList<Event?>()
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var event = value.getValue(Event::class.java)!!
                    var eventmod: Event = Event()
                    getEvent(event.id_event){

                        eventmod = it

                            var schoolController = SchoolController()
                        schoolController.getIdSchool(school) {
                            schoolController.getSchool(it) {

                                var school: String? = it.id_school
                                var event_schoolid: String? = eventmod.id_school
                                if (event_schoolid != null) {

                                        if (event_schoolid == school) {
                                            eventfilter.add(eventmod)
                                        }


                                }


                            }

                        }



                    }

                }
                SortbyStartDateEvent(eventfilter)
                callback.invoke(eventfilter)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
            }

    fun FilterCityEvent(city: String?, callback: (ArrayList<Event?>) -> Unit){

        val data = database.getReference("Events")
        var eventfilter : ArrayList<Event?> = ArrayList<Event?>()
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var event = value.getValue(Event::class.java)!!
                    var eventmod: Event = Event()
                    getEvent(event.id_event){

                        eventmod = it

                        var cityController = CityController()
                        cityController.getIdCity(city) {
                            cityController.getCity(it) {

                                var city: String? = it.id_city
                                var event_cityid: String? = eventmod.id_city
                                if (event_cityid != null) {

                                    if (event_cityid == city) {
                                        eventfilter.add(eventmod)
                                    }


                                }


                            }

                        }



                    }

                }
                SortbyStartDateEvent(eventfilter)
                callback.invoke(eventfilter)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    fun FilterEventInterestUser(city: String?, school : String?, musics: ArrayList<String?>,id_user : String,callback: (ArrayList<Boolean?>) -> Unit) {

        val data = database.getReference("Events")
        var interest: ArrayList<Boolean?> = ArrayList<Boolean?>()
        var events: ArrayList<Event?> = ArrayList<Event?>()

        if (city != null) {

            FilterCityEvent(city) {
                events = it
                for (event in events) {
                    var subs: SubscribeEvent? = SubscribeEvent()
                    var subsCon: SubscribeEventController = SubscribeEventController()

                    subsCon.getSubscribeEvent(event!!.id_subscribe_event) {
                        subs = it
                        var subsusers: ArrayList<String?> = ArrayList<String?>()
                        for (subsuser in subsusers) {
                            if (subsuser.equals(id_user)) {
                                interest.add(true)
                            } else {
                                interest.add(false)
                            }

                        }




                }
            }
                callback.invoke(interest)
        }

        }
        else if(school != null){
            FilterSchoolEvent(school) {
                events = it
                for (event in events) {
                    var subs: SubscribeEvent? = SubscribeEvent()
                    var subsCon: SubscribeEventController = SubscribeEventController()

                    subsCon.getSubscribeEvent(event!!.id_subscribe_event) {
                        subs = it
                        var subsusers: ArrayList<String?> = ArrayList<String?>()
                        for (subsuser in subsusers) {
                            if (subsuser.equals(id_user)) {
                                interest.add(true)
                            } else {
                                interest.add(false)
                            }

                        }




                    }
                }
                callback.invoke(interest)
            }

        }else if (musics != null){
            FilterMusicEvent(musics) {
                events = it
                for (event in events) {
                    var subs: SubscribeEvent? = SubscribeEvent()
                    var subsCon: SubscribeEventController = SubscribeEventController()

                    subsCon.getSubscribeEvent(event!!.id_subscribe_event) {
                        subs = it
                        var subsusers: ArrayList<String?> = ArrayList<String?>()
                        for (subsuser in subsusers) {
                            if (subsuser.equals(id_user)) {
                                interest.add(true)
                            } else {
                                interest.add(false)
                            }

                        }




                    }
                }
                callback.invoke(interest)
            }

        }

    }





    fun createEvent(name : String?, id_user_admin: String?, adresse: String?, zip: String?, city: String?, school : String?, musics : ArrayList<String>, start_date: String?, end_date: String?, description: String?, etudiant : String, limit_user: String?){

        val data = database.getReference("Events")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        val userController = UserController()
        Log.d("vfvf",id_user_admin)
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

        val event = Event(newId,name, id_user_admin, newId2, adresse,zip, id_city, id_school, id_musics,start_date, end_date, description,etudiant, limit_user!!.toInt(), date)
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
                    var event  =  value.getValue(Event::class.java)!!
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