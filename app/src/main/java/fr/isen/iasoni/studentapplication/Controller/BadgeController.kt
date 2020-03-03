package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.School
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BadgeController {



    val database = FirebaseDatabase.getInstance()
    fun getBadge (id_badge: String?,callback: (Badge) -> Unit ) {
        var badge : Badge = Badge()
        val myRef = database.getReference("Badges")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_badge)){

                        badge  = value.getValue(Badge::class.java)!!

                    }
                }
                callback.invoke(badge)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun BadgeExist(name : String?,callback: (Boolean) -> Unit ){
        val data = database.getReference("Badges")
        var exist : Boolean = false
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    var BadgeComp = value.getValue(Badge::class.java)!!
                    if(BadgeComp.name != name){

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

    fun addBadge(img : String?, name: String?){
        val data = database.getReference("Badges")
        val newId = data.push().key.toString()

       BadgeExist(name) {
           if(!it){
               val badge = Badge(newId,img, name)
               data.child(newId).setValue(badge)
           }

        }



    }


}