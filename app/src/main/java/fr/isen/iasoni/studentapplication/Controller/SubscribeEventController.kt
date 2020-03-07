package fr.isen.iasoni.studentapplication.Controller

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Event.SubscribeEvent
import fr.isen.iasoni.studentapplication.Modele.User.User

class SubscribeEventController {

    val database = FirebaseDatabase.getInstance()
    fun getSubscribeEvent(id_subscribe_event: String?,callback: (SubscribeEvent) -> Unit ) {
        var subsevent : SubscribeEvent = SubscribeEvent()
        val myRef = database.getReference("SubscribeEvent")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    if(value.key.equals(id_subscribe_event)){
                        subsevent  = value.getValue(SubscribeEvent::class.java)!!
                    }
                }
                callback.invoke(subsevent)
            }
            override fun onCancelled(error: DatabaseError) {
                //Log.d
            }
        })
    }



    fun addUserOnEvent(id_subscribe_event: String?, id_user: String){
        val data = database.getReference("SubscribeEvent/" + id_subscribe_event)

        var subsevent : SubscribeEvent = SubscribeEvent()
        getSubscribeEvent(id_subscribe_event) {
            subsevent = it
            var users: ArrayList<String>? = subsevent!!.users

            users!!.add(id_user)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("users", users)
            data.updateChildren(childUpdates)


        }

    }

}