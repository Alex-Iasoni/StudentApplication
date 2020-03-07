package fr.isen.iasoni.studentapplication.Controller

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.User.SwipeUser
import fr.isen.iasoni.studentapplication.Modele.User.User
import java.text.FieldPosition

class SwipeUserController {

    val database = FirebaseDatabase.getInstance()


    fun getSwipeUser(id_swipe: String?,callback: (SwipeUser) -> Unit ) {
        var swipe : SwipeUser = SwipeUser()
        val myRef = database.getReference("SwipeUser")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_swipe)){

                        swipe  = value.getValue(SwipeUser::class.java)!!

                    }
                }
                callback.invoke(swipe)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }

    fun InitSwipeUser(id_event : String?, position: Int?, id_user:String?){
        val data = database.getReference("SwipeUser")
        val newId = data.push().key.toString()

        var swipe = SwipeUser(newId,id_event, position,id_user)
        var user : UserController = UserController()
        user.editSwipeArray(id_user,newId)
        data.child(newId).setValue(swipe)
    }

    fun EditPosition(id_swipe: String?, position: Int){
        val data = database.getReference("SwipeUser/" + id_swipe)

        var swipe: SwipeUser = SwipeUser()

        getSwipeUser(id_swipe) {
            swipe = it


            swipe.position = position


            val childUpdates = HashMap<String, Any>()
            childUpdates.put("position", position)
            data.updateChildren(childUpdates)
        }

    }

}