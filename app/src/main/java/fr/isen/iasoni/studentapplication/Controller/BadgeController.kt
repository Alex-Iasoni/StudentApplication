package fr.isen.iasoni.studentapplication.Controller

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.School

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
}