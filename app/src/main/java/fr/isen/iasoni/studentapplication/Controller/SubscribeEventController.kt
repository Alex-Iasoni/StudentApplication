package fr.isen.iasoni.studentapplication.Controller

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.Event.SubscribeEvent
import fr.isen.iasoni.studentapplication.Modele.User.User

class SubscribeEventController {

    val database = FirebaseDatabase.getInstance()
    fun getSubscribeEvent(id_subscribe_event: String?, callback: (SubscribeEvent) -> Unit) {
        var subsevent: SubscribeEvent = SubscribeEvent()
        val myRef = database.getReference("SubscribeEvent")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children) {
                    if (value.key.equals(id_subscribe_event)) {
                        subsevent = value.getValue(SubscribeEvent::class.java)!!

                    }
                }
                callback.invoke(subsevent)
            }

            override fun onCancelled(error: DatabaseError) {
                //Log.d
            }
        })


    }


    fun addUserOnEvent(users: ArrayList<String>,id_subscribe_event: String, id_user: String) {
        IdSubscribeExist(id_subscribe_event) {
            Log.d("IdSuscribeExiste",it.toString())

            if (!it) {
                val dataPost = database.getReference("SubscribeEvent")
                var newusers = ArrayList<String>()
                newusers.add(id_user)
                val sub = SubscribeEvent(id_subscribe_event, newusers)
                dataPost.child(id_subscribe_event).setValue(sub)
            } else {
                val data = database.getReference("SubscribeEvent/" + id_subscribe_event)


                    var users_change : ArrayList<String> = ArrayList<String>()
                users_change = users

                        var exist = false
                        for (sub in users) {
                            if (sub == id_user) {
                                exist = true
                                break;
                            }
                        }
                        if (exist == false) {
                            if (users != null) {
                                users_change.add(id_user)
                                val childUpdates = HashMap<String, Any>()
                                Log.d("ADD DE USER",users_change.toString())

                                childUpdates.put("users", users_change)
                                data.updateChildren(childUpdates)

                            }

                    }





            }

        }

    }

    fun IdSubscribeExist(id_subscribe_event: String?, callback: (Boolean) -> Unit) {
        val data = database.getReference("SubscribeEvent")
        var exist: Boolean = false
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children) {

                    if (value.key != id_subscribe_event) {

                        exist = false
                    } else {
                        exist = true


                        break;
                    }
                }
                callback.invoke(exist)


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun deleteUseronEvent(users: ArrayList<String>,id_subscribe_event: String?, id_user: String) {
        val data = database.getReference("SubscribeEvent/" + id_subscribe_event)

            var users_change = ArrayList<String>()
        users_change = users
            var exist = false
            for (sub in users) {
                if (sub == id_user) {
                    exist = true
                    users_change.remove(id_user)
                }
            }
            if (exist == true) {
                val childUpdates = HashMap<String, Any>()
                Log.d("DELETE DE USER",users_change.toString())
                childUpdates.put("users", users_change)
                data.updateChildren(childUpdates)
            }




    }

    fun NumberSubscribeUser(id_subscribe_event: String, callback: (Int) -> Unit) {
        IdSubscribeExist(id_subscribe_event) {
            var Number: Int = 0
            if (it) {
                val data = database.getReference("SubscribeEvent")

               getSubscribeEvent(id_subscribe_event){
                   Number = it.users.size
               }

            }
            callback.invoke(Number)
        }

    }

}