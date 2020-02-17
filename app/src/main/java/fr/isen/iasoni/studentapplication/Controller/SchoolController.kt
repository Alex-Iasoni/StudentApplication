package fr.isen.iasoni.studentapplication.Controller

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.School

class SchoolController {

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
}