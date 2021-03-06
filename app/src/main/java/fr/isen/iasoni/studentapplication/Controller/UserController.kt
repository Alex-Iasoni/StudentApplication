package fr.isen.iasoni.studentapplication.Controller

import com.google.firebase.database.FirebaseDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.School
import fr.isen.iasoni.studentapplication.Modele.User.User
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UserController {

    companion object {
        private const val TAG = "WriteUser"
    }

    val database = FirebaseDatabase.getInstance()

    fun DateCurrent() : String{
        var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        return dateString
    }


    fun register(id: String, name: String?, surname: String?, email : String?, birthday: String?, school : String?, city : String?) {
        val formatted = DateCurrent()
        val  posts : ArrayList<String>? = ArrayList<String>()

        val schoolC = SchoolController()
      schoolC.getIdSchool(school){
          var id_school : String? = ""

          id_school = it

        val cityC = CityController()
        cityC.getIdCity(city){
            var id_city : String? = ""
            id_city = it

        val dataPost = database.getReference("Users")
        val user = User(id,name, surname, email, birthday, id_school, id_city,"false", "none")
        dataPost.child(id).setValue(user)

        }
      }

    }

    fun getUser(id_user: String?,callback: (User) -> Unit ) {
        var user : User = User()
        val myRef = database.getReference("Users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_user)){

                        user  = value.getValue(User::class.java)!!
                        break;
                    }
                }
                callback.invoke(user)
            }
            override fun onCancelled(error: DatabaseError) {
                //Log.d
            }
        })
    }

    fun editEventArray(type : String?, id_user: String?, id_event: String) {
        val data = database.getReference("Users/" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it
            var events: ArrayList<String> = user.events
            if(events.contains(id_event) == false ) {
                if(type == "add"){
                    events.add(id_event)
                }
                else{
                    events.remove(id_event)
                }
                val childUpdates = HashMap<String, Any>()
                childUpdates.put("events", events)
                data.updateChildren(childUpdates)

            }
        }


    }

    fun editBadgeArray(id_user: String?, id_badge: String) {
        val data = database.getReference("Users/" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it
            var exist = true
            if(user.badges != null){


            for(id in user.badges){
                if(id_badge != id){
                    exist = true
                }
                else{
                    exist = false
                    break;
                }
            }
            }
            if(exist == true){
                Log.d("dee","DDDDD")
                var badges: ArrayList<String>? = ArrayList<String>()
                badges!!.addAll(user.badges)
                badges!!.add(id_badge)

                val childUpdates = HashMap<String, Any>()
                childUpdates.put("badges", badges)
                data.updateChildren(childUpdates)
            }

        }
    }

    fun editSwipeArray(id_user: String?, id_swipe: String) {
        val data = database.getReference("Users/" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it
            var exist = true
            if(user.swipes != null){


                for(id in user.swipes){
                    if(id_swipe != id){
                        exist = true
                    }
                    else{
                        exist = false
                        break;
                    }
                }
            }
            if(exist == true) {

                var swipes: ArrayList<String>? =ArrayList<String>()
                swipes!!.addAll(user.swipes)
                swipes!!.add(id_swipe)

                val childUpdates = HashMap<String, Any>()
                childUpdates.put("swipes", swipes)
                data.updateChildren(childUpdates)
            }
        }
    }

    fun editEventAdminArray(id_user: String?, id_event: String) {
               val data = database.getReference("Users/" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it


                var events_admin: ArrayList<String> = user.events_admin
            if(events_admin.contains(id_event) == false ) {
                events_admin.add(id_event)

                val childUpdates = HashMap<String, Any>()
                childUpdates.put("events_admin", events_admin)
                data.updateChildren(childUpdates)
            }
        }
    }
    fun UserCertified(id_user: String?, callback: (String) -> Unit){
        var certified = "false"
        var user: User = User()
        getUser(id_user){
            user = it
            if(user.certified.equals("true")){
                certified = "true"
            }else{
                certified = "false"
            }
            callback.invoke(certified)
        }

    }



}