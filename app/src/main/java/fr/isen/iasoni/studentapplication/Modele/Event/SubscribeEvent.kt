package fr.isen.iasoni.studentapplication.Modele.Event

import com.google.firebase.database.PropertyName

data class SubscribeEvent (

    @PropertyName("id_subscribe_event") val id_subscribe_event: String?,
    @PropertyName("users") val users: ArrayList<String>


)


{
    constructor() : this(null, arrayListOf())
}
