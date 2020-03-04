package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.School
import fr.isen.iasoni.studentapplication.Modele.User.User
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SchoolController {

    fun DateCurrent() : String{
        var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        return dateString
    }
    val database = FirebaseDatabase.getInstance()

    fun getSchools(callback: ( ArrayList<School>) -> Unit ) {
        var schools : ArrayList<School> = ArrayList<School>()
        val myRef = database.getReference("Schools")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    var school : School = School()
                    school  = value.getValue(School::class.java)!!
                    schools.add(school)



                }
                callback.invoke(schools)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun getSchool(school_id: String?,callback: (School) -> Unit ) {
        var school : School = School()
        val myRef = database.getReference("Schools")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(school_id)){

                        school  = value.getValue(School::class.java)!!

                    }
                }
                callback.invoke(school)

            }
            override fun onCancelled(error: DatabaseError) {

               //Log.d
            }
        })

    }


    fun getIdSchool(name : String?,callback: (String?) -> Unit ) {

    var idSchool : String? = ""
        val myRef = database.getReference("Schools")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){


                    var school  =   value.getValue(School::class.java)!!
                    if(school.name.equals(name)){

                        idSchool = school.id_school

                    }
                }
                callback.invoke(idSchool)
            }
            override fun onCancelled(error: DatabaseError) {

               //Log.d
            }
        })


    }

    fun editUserArray(id_school: String?, id_user: String) {
        val data = database.getReference("Schools" + id_school)

        var school: School = School()
        getSchool(id_school) {
            school = it


            var users: ArrayList<String>? = school!!.users

            users!!.add(id_user)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("users", users)
            data.updateChildren(childUpdates)
        }
    }

    fun editEventArray(id_school: String?, id_event: String) {
        val data = database.getReference("Schools" + id_school)

        var school: School = School()
        getSchool(id_school) {
            school = it


            var events: ArrayList<String>? = school!!.event

            events!!.add(id_event)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("event", events)
            data.updateChildren(childUpdates)
        }
    }

    fun SchoolExist(name : String?, callback: (Boolean) -> Unit){
        val data = database.getReference("Schools")

        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var exist : Boolean = false
                for (value in dataSnapshot.children){

                    var SchoolComp = value.getValue(School::class.java)!!
                    if(SchoolComp.name != name){

                        exist = false
                    }else{
                        exist = true
                    }
                }
                callback.invoke(exist)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun addSchool(name: String?, adresse : String?, img: String?){
        val data = database.getReference("Schools")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        SchoolExist(name){
            if(it == false){
                var school = School(newId,name, adresse,img, date)

                data.child(newId).setValue(school)
            }

        }
    }

}