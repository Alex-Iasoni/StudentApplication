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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserController {

    companion object {
        private const val TAG = "WriteUser"
    }

    val database = FirebaseDatabase.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateCurrent() : String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun Register(id: String, name: String?, surname: String?, email : String?, birthday: String?, school : String?, city : String?) {
        val formatted = DateCurrent()
        val  posts : ArrayList<String>? = ArrayList<String>()

        val schoolC = SchoolController()
        val id_school = schoolC.getIdSchool(school)


        val dataPost = database.getReference("Users")
        val user = User(id,name, surname, email, birthday, id_school, city)
        dataPost.child(id).setValue(user)

    }

    fun getUser(id_user: String?,callback: (User) -> Unit ) {
        var user : User = User()
        val myRef = database.getReference("Users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_user)){

                        user  = value.getValue(User::class.java)!!

                    }
                }
                callback.invoke(user)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun editEventArray(id_user: String?, id_event: String) {
        val data = database.getReference("Users" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it
            var events: ArrayList<String>? = user!!.events

            events!!.add(id_event)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("events", events)
            data.updateChildren(childUpdates)


        }


    }

    fun editBadgeArray(id_user: String?, id_badge: String) {
        val data = database.getReference("Users" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it


            var badges: ArrayList<String>? = user!!.badges

            badges!!.add(id_badge)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("badges", badges)
            data.updateChildren(childUpdates)
        }
    }

    fun editSwipeArray(id_user: String?, id_swipe: String) {
        val data = database.getReference("Users" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it


            var swipes: ArrayList<String>? = user!!.swipes

            swipes!!.add(id_swipe)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("swipes", swipes)
            data.updateChildren(childUpdates)
        }
    }

    fun editEventAdminArray(id_user: String?, id_event: String) {
        val data = database.getReference("Users" + id_user)

        var user: User = User()
        getUser(id_user) {
            user = it


            var events_admin: ArrayList<String>? = user!!.events_admin

            events_admin!!.add(id_event)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("events_admin", events_admin)
            data.updateChildren(childUpdates)
        }
    }


}