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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SchoolController {
    @RequiresApi(Build.VERSION_CODES.O)
    fun DateCurrent() : String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted
    }
    val database = FirebaseDatabase.getInstance()
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


    fun getIdSchool(name : String?) : String?{

    var idSchool : String? = ""
        val myRef = database.getReference("Schools")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){


                    var school  =   value.getValue(School::class.java)!!
                    if(school.name.equals(name)){

                        idSchool = school.getIdSchool()

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

               //Log.d
            }
        })
        return idSchool

    }

    fun editUserArray(id_school: String?, id_user: String) {
        val data = database.getReference("School" + id_school)

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

    fun SchoolExist(name : String?) : Boolean{
        val data = database.getReference("Schools")
        var exist : Boolean = false
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    var SchoolComp = value.getValue(School::class.java)!!
                    if(SchoolComp.name != name){

                        exist = false
                    }else{
                        exist = true
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return exist
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addSchool(name: String?, adresse : String?, img: String?){
        val data = database.getReference("Schools")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        if(!SchoolExist(name)){
            var school = School(newId,name, adresse,img, date)
            data.child(newId).setValue(school)
        }
    }

}